package com.example.audiourl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.app.DownloadManager;
import android.widget.Toast;

public class DownloadCompleteReceiver extends BroadcastReceiver  {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long receivedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (receivedDownloadId != -1) { // Verificar si se recibió un ID de descarga válido
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(receivedDownloadId);
                try (Cursor cursor = downloadManager.query(query)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int status = cursor.getInt(columnIndex);
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            int fileUriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                            String downloadedFilePath = cursor.getString(fileUriIndex);
                            if (downloadedFilePath != null) {
                                // Llamar a la actividad para manejar la descarga completada
                                MainActivity.handleDownloadComplete(context, downloadedFilePath);
                            } else {
                                //MainActivity.showToastStatic(context, "Error al obtener el archivo descargado");
                            }
                        } else {
                            //MainActivity.showToastStatic(context, "Error en la descarga");
                        }
                    } else {
                        System.out.println(context + "Error al obtener información de la descarga");
                    }
                }
            } else {
                System.out.println(context + "ID de descarga no válido");
            }
        }
    }
}
