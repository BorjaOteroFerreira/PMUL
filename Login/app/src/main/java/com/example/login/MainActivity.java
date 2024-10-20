package com.example.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.modelo.AsistenteBD;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText etNombre, etPassword;
    AsistenteBD asistenteBd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asistenteBd = AsistenteBD.getInstance(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etNombre = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        asistenteBd.insertarUsuarioInicial();
        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String nombre = etNombre.getText().toString();
        String password = etPassword.getText().toString();
        AsistenteBD asistenteBD = AsistenteBD.getInstance(this);
        ArrayList<String> usuario = asistenteBD.getUsuarioYcontraseña(nombre, password);
        if (usuario.size() < 1) {
            Snackbar.make(findViewById(R.id.main), "Login Incorrecto", Snackbar.LENGTH_LONG).show();
        } else if (password.equals(usuario.get(1)) && nombre.equals(usuario.get(0))) {
                Snackbar.make(findViewById(R.id.main), "Login correcto", Snackbar.LENGTH_LONG).show();
            }
        }
}