package com.example.audiourl;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import android.Manifest;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AudioDownloadPlayerActivity";
    private static final int PERMISSION_REQUEST_CODE = 1;

    private static final String AUDIO_URL = "https://raw.githubusercontent.com/borjaoteroferreira/Apuntes/master/sigmaboy.mp3";

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private Button playPauseButton;
    private DownloadManager downloadManager;
    private long downloadId;

    // Estado para saber si el audio está en reproducción
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar servicios
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        // Configurar botón de reproducción
        playPauseButton = findViewById(R.id.playPauseButton);
        playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                pauseAudio();
            } else {
                checkPermissionsAndDownload();
            }
        });
    }

    private void checkPermissionsAndDownload() {
        // Lista de permisos necesarios
        String[] permissions;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Permiso para Android 13 y superior
            permissions = new String[] {
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_MEDIA_AUDIO
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Permisos para Android 6 a 12
            permissions = new String[] {
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        } else {
            // No es necesario solicitar permisos en tiempo de ejecución para Android 5 y versiones anteriores
            downloadAndPlayAudio();
            return;
        }

        // Verificar si los permisos ya están concedidos
        boolean permissionsNeeded = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded = true;
                break; // Salir del bucle si se encuentra un permiso no concedido
            }
        }

        // Solicitar permisos si alguno no está concedido
        if (permissionsNeeded) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        } else {
            downloadAndPlayAudio(); // Iniciar descarga si todos los permisos están concedidos
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                downloadAndPlayAudio();
            } else {
                showToast("Permisos denegados. No se puede descargar el audio.");
            }
        }
    }

    private void downloadAndPlayAudio() {
        // Crear directorio de descargas
        File downloadDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (downloadDir == null) {
            showToast("No se puede acceder al directorio de descargas");
            return;
        }

        // Nombre de archivo único
        String fileName = "downloaded_audio_" + System.currentTimeMillis() + ".mp3";
        File destinationFile = new File(downloadDir, fileName);

        // Configurar solicitud de descarga
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(AUDIO_URL))
                .setTitle("Descargando audio")
                .setDescription("Descargando archivo de audio")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(destinationFile))
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);

        // Iniciar descarga
        downloadId = downloadManager.enqueue(request);

        // Registrar el receptor para manejar la descarga completada
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);
    }

    private final BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long receivedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if (receivedDownloadId == downloadId) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);

                    try (Cursor cursor = downloadManager.query(query)) {
                        if (cursor.moveToFirst()) {
                            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            int status = cursor.getInt(columnIndex);

                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                int fileUriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                                String downloadedFilePath = cursor.getString(fileUriIndex);

                                if (downloadedFilePath != null) {
                                    playDownloadedAudio(downloadedFilePath);
                                } else {
                                    showToast("Error al obtener el archivo descargado");
                                }
                            } else {
                                showToast("Error en la descarga");
                            }
                        }
                    }
                }
            }
        }
    };

    private void playDownloadedAudio(String filePath) {
        if (mediaPlayer != null) {
            mediaPlayer.release();  // Liberar cualquier recurso anterior
            mediaPlayer = null;
        }

        try {
            mediaPlayer = new MediaPlayer();
            Uri fileUri = Uri.parse(filePath);
            mediaPlayer.setDataSource(this, fileUri);

            // Cuando el audio esté preparado, comenzar a reproducir
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                Log.d(TAG, "Audio comenzó a reproducirse correctamente");
                showToast("Reproduciendo audio");
            });

            // Manejar errores en la reproducción
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "Error de reproducción. What: " + what + ", Extra: " + extra);
                showToast("Error de reproducción");
                return false;
            });

            // Cuando la reproducción termine
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Reproducción de audio completada");
                showToast("Reproducción completada");
            });

            // Preparar el reproductor de audio de manera asíncrona
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(TAG, "Error al configurar MediaPlayer", e);
            showToast("Error al configurar MediaPlayer");
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            playPauseButton.setText("Reproducir");
            showToast("Audio pausado");
        }
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        try {
            unregisterReceiver(downloadReceiver);
        } catch (IllegalArgumentException e) {
            // Receptor ya desregistrado
        }
    }
    public static void handleDownloadComplete(Context context, String downloadedFilePath) {
        // Comprobar si el contexto es una instancia de MainActivity
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            activity.playDownloadedAudio(downloadedFilePath);
        } else {
            Log.e(TAG, "Error: Contexto no es una instancia de MainActivity");
        }
    }
}
