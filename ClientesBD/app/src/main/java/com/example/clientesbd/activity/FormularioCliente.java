package com.example.clientesbd.activity;
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

import java.util.ArrayList;

public class FormularioCliente extends AppCompatActivity {

    EditText etNombre, etApellido, etLongitud, etLatitud;
    Spinner etProvincia;
    CheckBox etVip;
    Button btnGuardar , btnVolver, btnEditar;
    AsistenteBD asistenteBd;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        extras = getIntent().getExtras();
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

        btnVolver.setOnClickListener(v -> { setResult(RESULT_OK);
                                            finish();});
        btnGuardar.setOnClickListener(v -> guardarFormulario());
        btnEditar.setOnClickListener(v -> insertarFormulario());

        montarInterfaz();
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

    private void montarInterfaz(){
        int idCliente = extras != null ? extras.getInt("idCliente", -1) : -1;
        if (idCliente != -1) {
            intercambiarBotones();
            cargarProvincias();
            rellenarFormulario(idCliente);
        }
        else{
            btnEditar.setVisibility(View.GONE);
        }
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
    private void cargarProvincias() {
        ArrayList<String> provincias = asistenteBd.getProvincias();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provincias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etProvincia.setAdapter(adapter);
    }

    private void marcarProvinciaCliente(String provincia) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) etProvincia.getAdapter();
        int position = adapter.getPosition(provincia);
        etProvincia.setSelection(position);
    }

    private void rellenarFormulario(int idCliente){
        Cliente cliente = asistenteBd.getClientePorId(this, idCliente);
        marcarProvinciaCliente(cliente.getProvincia());
        etNombre.setText(cliente.getNombre());
        etApellido.setText(cliente.getApellido());
        etVip.setChecked(cliente.isVip() == 1);
        etLongitud.setText(cliente.getLongitud());
        etLatitud.setText(cliente.getLatitud());
    }

}