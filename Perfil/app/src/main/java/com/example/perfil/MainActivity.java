package com.example.perfil;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {

    private Button btnPerfil;
    private TextView lblNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnVolver), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lblNombre = findViewById(R.id.lblNombre);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(v -> abrirPerfil());
        cargarNombre();
    }

    private void abrirPerfil() {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    private void cargarNombre() {
        Ajustes ajustes = Ajustes.getInstance(this);
        String nombre = ajustes.getNombre();
        lblNombre.setText("¡Hola " + nombre + "!");
    }
}