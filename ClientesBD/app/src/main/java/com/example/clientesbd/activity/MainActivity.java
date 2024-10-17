package com.example.clientesbd.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clientesbd.R;
import com.example.clientesbd.databinding.ActivityMainBinding;
import com.example.clientesbd.modelo.AsistenteBD;
import com.example.clientesbd.modelo.Cliente;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListView lista;
    AsistenteBD asistenteBd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.fab.setOnClickListener(v -> cambiarActivity() );
        lista = findViewById(R.id.listaClientes);
        asistenteBd = AsistenteBD.getInstance(this);
        mostrarClientes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearClientesFicticios(){
        Cliente cliente = new Cliente("Borja",
                                      "Otero",
                                     "Orense",
                                          true,
                                      "40.416775",
                                        "-3.703790");
        asistenteBd.addCliente(cliente);

    }

    private void mostrarClientes(){
        lista.setAdapter(asistenteBd.getClientes(this));


    }

    private void cambiarActivity() {
        Intent intent = new Intent(this, FormularioCliente.class);
        startActivity(intent);
    }


}