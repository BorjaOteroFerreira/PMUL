package com.example.detectarobjetos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import androidx.camera.core.ImageProxy;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class ImageUtils {
    // El modelo espera valores normalizados [0,1]
    private static final float SCALE = 1.0f / 255.0f;

    public static ByteBuffer bitmapToByteBuffer(Bitmap bitmap, int inputSize) {
        // 1,228,800 bytes = 640*640*3 (RGB) bytes
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3);
        byteBuffer.order(java.nio.ByteOrder.nativeOrder());

        int[] intValues = new int[inputSize * inputSize];
        bitmap.getPixels(intValues, 0, inputSize, 0, 0, inputSize, inputSize);

        // Convertir la imagen a formato planar float32 [0-1]
        for (int i = 0; i < inputSize * inputSize; ++i) {
            final int val = intValues[i];
            byteBuffer.put((byte) ((val >> 16) & 0xFF));
            byteBuffer.put((byte) ((val >> 8) & 0xFF));
            byteBuffer.put((byte) (val & 0xFF));
        }

        return byteBuffer;
    }

    public static Bitmap imageProxyToBitmap(ImageProxy image) {
        ImageProxy.PlaneProxy[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];

        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21,
                image.getWidth(), image.getHeight(), null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(
                new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()),
                100, out);

        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        Matrix matrix = new Matrix();
        matrix.postRotate(image.getImageInfo().getRotationDegrees());
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int targetSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleRatio = Math.max(
                (float) targetSize / width,
                (float) targetSize / height
        );

        int scaledWidth = Math.round(width * scaleRatio);
        int scaledHeight = Math.round(height * scaleRatio);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);

        // Recortar al tamaño objetivo
        int startX = (scaledWidth - targetSize) / 2;
        int startY = (scaledHeight - targetSize) / 2;

        return Bitmap.createBitmap(
                scaledBitmap,
                Math.max(0, startX),
                Math.max(0, startY),
                targetSize,
                targetSize
        );
    }
}