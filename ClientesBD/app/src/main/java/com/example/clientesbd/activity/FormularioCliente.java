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
import com.example.clientesbd.modelo.Provincia;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FormularioCliente extends AppCompatActivity {

    EditText etNombre, etApellido, etLongitud, etLatitud;
    Spinner spProvincia;
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
        cargarWidgets();
        montarInterfaz();
        btnVolver.setOnClickListener(v -> { setResult(RESULT_OK);
                                            finish();});
        btnGuardar.setOnClickListener(v -> guardarDatosCliente());
        btnEditar.setOnClickListener(v -> editarDatosCliente());
    }


    private void cargarWidgets(){
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        spProvincia = findViewById(R.id.spinnerProvincia);
        etVip = findViewById(R.id.cbVip);
        etLongitud = findViewById(R.id.etLongitud);
        etLatitud = findViewById(R.id.etLatitud);
        btnEditar = findViewById(R.id.btnEditar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        asistenteBd = AsistenteBD.getInstance(this);
    }

    private void montarInterfaz(){
        cargarProvincias();
        int idCliente = extras != null ? extras.getInt("idCliente", -1) : -1;
        boolean modoEdicion = idCliente != -1;
        if (modoEdicion) {
            btnGuardar.setVisibility(View.GONE);
            rellenarFormulario(idCliente);
        }
        else{ btnEditar.setVisibility(View.GONE); }
    }

    private void cargarProvincias() {
        ArrayList<Provincia> provincias = asistenteBd.getProvincias();
        ArrayAdapter<Provincia> adapter = new ArrayAdapter<>(this,
                                                          android.R.layout.simple_spinner_item,
                                                          provincias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincia.setAdapter(adapter);
    }

    private void rellenarFormulario(int idCliente){
        Cliente cliente = asistenteBd.getClientePorId(idCliente);
        marcarProvinciaCliente(cliente.getProvincia());
        etNombre.setText(cliente.getNombre());
        etApellido.setText(cliente.getApellido());
        etVip.setChecked(cliente.isVip() == 1);
        etLongitud.setText(cliente.getLongitud());
        etLatitud.setText(cliente.getLatitud());
    }

    private void marcarProvinciaCliente(Provincia provincia) {
        ArrayAdapter<Provincia> adapter = (ArrayAdapter<Provincia>) spProvincia.getAdapter();
        int position = adapter.getPosition(provincia);
        spProvincia.setSelection(position);
    }

    private void editarDatosCliente() {
        int idCliente = getIntent().getExtras().getInt("idCliente");
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        boolean vip = etVip.isChecked();
        String longitud = etLongitud.getText().toString();
        String latitud = etLatitud.getText().toString();
        int idProvincia = ((Provincia) spProvincia.getSelectedItem()).getId();
        String nombreProvincia = spProvincia.getSelectedItem().toString();
        Provincia provincia = new Provincia(idProvincia, nombreProvincia);
        Cliente cliente = new Cliente(idCliente, nombre, apellido, provincia, vip, longitud, latitud);
        asistenteBd.updateCliente(cliente);
        Snackbar.make(findViewById(R.id.main), "Cliente editado con éxito", Snackbar.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    private void guardarDatosCliente(){
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        int idProvincia = ((Provincia) spProvincia.getSelectedItem()).getId();
        String nombreProvincia = spProvincia.getSelectedItem().toString();
        Provincia provincia = new Provincia(idProvincia, nombreProvincia);
        boolean vip = etVip.isChecked();
        String longitud = etLongitud.getText().toString();
        String latitud = etLatitud.getText().toString();
        Cliente cliente = new Cliente(nombre, apellido, provincia, vip, longitud, latitud);
        asistenteBd.addCliente(cliente);
        Snackbar.make(findViewById(R.id.main), "Cliente guardado con éxito", Snackbar.LENGTH_LONG).show();
    }
}