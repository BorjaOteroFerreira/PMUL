
package com.example.lmstudio;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lmstudio.ChatActivity;
import com.example.lmstudio.R;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    private String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView btnBackend1 = findViewById(R.id.btnBackend1);
        MaterialCardView btnBackend2 = findViewById(R.id.btnBackend2);
        EditText urlHost = findViewById(R.id.etUrl);

        btnBackend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!urlHost.getText().toString().isEmpty()) {
                    navigateToChatActivity("http://" + urlHost.getText().toString() + ":1234/");
                } else{
                    Toast.makeText(MainActivity.this, "Introduce una URL valida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBackend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!urlHost.getText().toString().isEmpty()) {
                    navigateToChatActivity("http://" + urlHost.getText().toString() +":11434/");
                }
                else{
                    Toast.makeText(MainActivity.this, "Introduce una URL valida", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void navigateToChatActivity(String backendName) {
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("BACKEND_URL", backendName);
        startActivity(intent);
    }
}