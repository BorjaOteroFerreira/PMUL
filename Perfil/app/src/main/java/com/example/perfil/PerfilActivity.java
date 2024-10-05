package com.example.perfil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PerfilActivity extends AppCompatActivity {

    EditText editTextNombre, editTextEdad;
    CheckBox checkBox;
    Button btnGuardar;
    Button btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnVolver), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        checkBox = findViewById(R.id.checkBox);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            volverAlMainActivity();
        });

        cargarDatos();
        btnGuardar.setOnClickListener(v -> {
            almacenarDatos();
            Toast.makeText(this, "Los datos han sido guardados", Toast.LENGTH_SHORT).show();
            cargarDatos();
        });
    }

    public void almacenarDatos() {
        String nombre = editTextNombre.getText().toString();
        int edad = Integer.parseInt(editTextEdad.getText().toString());
        boolean casado = checkBox.isChecked();
        SharedPreferences sp = getSharedPreferences("sp.xml", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nombre", nombre)
                .putInt("edad", edad)
                .putBoolean("casado", casado).apply();
    }

    public void cargarDatos() {
        SharedPreferences sp = getSharedPreferences("sp.xml", MODE_PRIVATE);
        String nombre = sp.getString("nombre", "");
        int edad = sp.getInt("edad", 0);
        boolean casado = sp.getBoolean("casado", false);
        editTextNombre.setText(nombre);
        editTextEdad.setText(String.valueOf(edad));
        checkBox.setChecked(casado);
    }

    public void volverAlMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}