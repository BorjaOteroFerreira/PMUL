package com.example.clientesbd.activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.clientesbd.modelo.AsistenteBD;
import com.example.clientesbd.modelo.Cliente;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clientesbd.R;

public class FormularioCliente extends AppCompatActivity {

    EditText etNombre, etApellido, etLongitud, etLatitud;
    Spinner etProvincia;
    CheckBox etVip;
    Button btnGuardar , btnVolver, btnEditar;
    AsistenteBD asistenteBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_formulario_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etProvincia = findViewById(R.id.spinnerProvincia);
        etVip = findViewById(R.id.cbVip);
        etLongitud = findViewById(R.id.etLongitud);
        etLatitud = findViewById(R.id.etLatitud);
        btnEditar = findViewById(R.id.btnEditar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        asistenteBd = AsistenteBD.getInstance(this);

        if (extras != null) {
            intercambiarBotones();
            int idCliente = extras.getInt("idCliente");
            Cliente cliente = asistenteBd.getClientePorId(this, idCliente);
            etNombre.setText(cliente.getNombre());
            etApellido.setText(cliente.getApellido());
            etProvincia.setSelection(3);//asistenteBd.getProvincias().indexOf(cliente.getProvincia()));
            etVip.setChecked(cliente.isVip() == 1);
            etLongitud.setText(cliente.getLongitud());
            etLatitud.setText(cliente.getLatitud());
        }
        else{
            btnEditar.setVisibility(View.GONE);
        }
        btnVolver.setOnClickListener(v -> { setResult(RESULT_OK);
                                            finish();
                                            });
        btnGuardar.setOnClickListener(v -> guardarFormulario());
        btnEditar.setOnClickListener(v -> insertarFormulario());
    }

    private void insertarFormulario() {
        int idCliente = getIntent().getExtras().getInt("idCliente");
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String provincia = etProvincia.getSelectedItem().toString();
        boolean vip = etVip.isChecked();
        String longitud = etLongitud.getText().toString();
        String latitud = etLatitud.getText().toString();
        Cliente cliente = new Cliente(idCliente, nombre, apellido, provincia, vip, longitud, latitud);
        asistenteBd.updateCliente(cliente);
        setResult(RESULT_OK);
        finish();
    }

    private void intercambiarBotones(){
        btnGuardar.setVisibility(View.GONE);
        btnEditar.setVisibility(View.VISIBLE);
    }
    private void guardarFormulario(){
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String provincia = etProvincia.getSelectedItem().toString();
            boolean vip = etVip.isChecked();
            String longitud = etLongitud.getText().toString();
            String latitud = etLatitud.getText().toString();
            Cliente cliente = new Cliente(nombre, apellido, provincia, vip, longitud, latitud);
            asistenteBd.addCliente(cliente);
    }
}