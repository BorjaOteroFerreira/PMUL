package com.example.detectarobjetos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private PreviewView viewFinder;
    private DetectionOverlay overlayView;
    private ExecutorService cameraExecutor;
    private Interpreter tflite;
    private ImageProcessor imageProcessor;

    private static final int MODEL_INPUT_SIZE = 640;
    private static final int NUM_BOXES = 6300;
    private static final int NUM_CLASSES = 80;
    private static final float SCORE_THRESHOLD = 0.0f;

    private final String[] labels = {"persona", "bicicleta", "carro", "motocicleta", "avión", "bus",
            "tren", "camión", "bote", "semáforo", "botella", "libro", "libreta", "mesa", "monitor",
            "teclado", "raton" /* ... añadir resto de etiquetas ... */};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFinder = findViewById(R.id.viewFinder);
        overlayView = new DetectionOverlay(this);
        addContentView(overlayView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        cameraExecutor = Executors.newSingleThreadExecutor();

        // Inicializar el procesador de imágenes
        imageProcessor = new ImageProcessor.Builder()
                .add(new ResizeOp(MODEL_INPUT_SIZE, MODEL_INPUT_SIZE, ResizeOp.ResizeMethod.BILINEAR))
                .build();

        // Cargar modelo
        try {
            File modelFile = loadModelFile();
            tflite = new Interpreter(modelFile);
        } catch (IOException e) {
            Log.e(TAG, "Error al cargar el modelo", e);
            Toast.makeText(this, "Error al cargar el modelo", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(640, 640))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, this::analyzeImage);

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error al iniciar la cámara", e);
                Toast.makeText(this, "Error al iniciar la cámara", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void analyzeImage(ImageProxy image) {
        // Convertir imagen a formato TensorImage
        TensorImage tensorImage = TensorImage.fromBitmap(
                ImageUtils.imageProxyToBitmap(image));
        tensorImage = imageProcessor.process(tensorImage);

        // Preparar buffers de entrada y salida
        ByteBuffer inputBuffer = tensorImage.getBuffer();
        float[][][] outputBuffer = new float[1][NUM_BOXES][NUM_CLASSES + 5];

        // Ejecutar inferencia
        tflite.run(inputBuffer, outputBuffer);

        // Procesar resultados
        List<DetectionResult> detections = new ArrayList<>();

        for (int i = 0; i < NUM_BOXES; i++) {
            float confidence = outputBuffer[0][i][4];
            if (confidence > SCORE_THRESHOLD) {
                float x = outputBuffer[0][i][0] / MODEL_INPUT_SIZE;
                float y = outputBuffer[0][i][1] / MODEL_INPUT_SIZE;
                float w = outputBuffer[0][i][2] / MODEL_INPUT_SIZE;
                float h = outputBuffer[0][i][3] / MODEL_INPUT_SIZE;

                // Encontrar la clase con mayor probabilidad
                int bestClass = -1;
                float bestScore = 0;
                for (int j = 0; j < NUM_CLASSES; j++) {
                    float score = outputBuffer[0][i][5 + j];
                    if (score > bestScore) {
                        bestScore = score;
                        bestClass = j;
                    }
                }

                if (bestClass >= 0) {
                    DetectionResult detection = new DetectionResult(
                            new RectF(x - w/2, y - h/2, x + w/2, y + h/2),
                            labels[bestClass],
                            confidence
                    );
                    detections.add(detection);

                    Log.d(TAG, "Detección: " + labels[bestClass] + " - Confianza: " + confidence);
                }
            }
        }

        // Actualizar overlay
        runOnUiThread(() -> {
            overlayView.setDetections(detections);
            Log.d(TAG, "Número de detecciones: " + detections.size());
        });

        image.close();
    }

    private File loadModelFile() throws IOException {
        File modelFile = new File(getCacheDir(), "yolov5.tflite");
        if (!modelFile.exists()) {
            InputStream inputStream = getAssets().open("yolov5.tflite");
            FileOutputStream outputStream = new FileOutputStream(modelFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
        }
        return modelFile;
    }

    private boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Se requieren permisos de cámara para usar esta app", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
        if (tflite != null) {
            tflite.close();
        }
    }
}