// MainActivity.java
package com.example.lmstudio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView btnBackend1 = findViewById(R.id.btnBackend1);
        MaterialCardView btnBackend2 = findViewById(R.id.btnBackend2);
        MaterialCardView btnBackend3 = findViewById(R.id.btnBackend3);

        EditText urlHost = findViewById(R.id.etUrl);
        urlHost.setText("http://192.168.1.47");
        btnBackend1.setOnClickListener(v -> {
            String url = urlHost.getText().toString().trim();
            if (isValidUrl(url)) {
                navigateToChatActivity(url + ":1234/");
            } else {
                Toast.makeText(MainActivity.this, "Introduce una URL válida", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackend2.setOnClickListener(v -> {
            String url = urlHost.getText().toString().trim();
            if (isValidUrl(url)) {
                navigateToChatActivity(url + ":11434/");
            } else {
                Toast.makeText(MainActivity.this, "Introduce una URL válida", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    private void navigateToChatActivity(String backendUrl) {
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("BACKEND_URL", backendUrl);
        startActivity(intent);
    }
}