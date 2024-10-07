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
    Ajustes ajustes;
    EditText editTextNombre, editTextEdad;
    CheckBox checkBox;
    Button btnGuardar, btnVolver;

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

        ajustes = Ajustes.getInstance(this);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        checkBox = findViewById(R.id.checkBox);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> volverAlMainActivity());
        cargarDatos();
        btnGuardar.setOnClickListener(v -> {
            almacenarDatos();
            Toast.makeText(this, "Datos almacenados", Toast.LENGTH_SHORT).show();
            cargarDatos();
        });
    }

    public void almacenarDatos() {
        ajustes.setNombre(editTextNombre.getText().toString());
        ajustes.setEdad(Integer.parseInt(editTextEdad.getText().toString()));
        ajustes.setCasado(checkBox.isChecked());
        ajustes.guardarDatos();
    }

    public void cargarDatos() {
        editTextNombre.setText(ajustes.getNombre());
        editTextEdad.setText(String.valueOf(ajustes.getEdad()));
        checkBox.setChecked(ajustes.getCasado());
    }

    public void volverAlMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}