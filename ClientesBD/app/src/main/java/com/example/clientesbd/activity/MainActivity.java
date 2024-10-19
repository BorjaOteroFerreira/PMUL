package com.example.clientesbd.activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clientesbd.R;
import com.example.clientesbd.databinding.ActivityMainBinding;
import com.example.clientesbd.modelo.AsistenteBD;
import com.example.clientesbd.modelo.Cliente;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListView lista;
    AsistenteBD asistenteBd;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.fab.setOnClickListener(v -> cargarFormularioCrear());
        lista = findViewById(R.id.listaClientes);
        asistenteBd = AsistenteBD.getInstance(this);
        asistenteBd.insertarProvinciasIniciales();
        lista.setOnItemClickListener((parent, view, position, id) -> cargarFormularioEditar(position));
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> EstamosDeVuelta(result.getResultCode())
        );


        mostrarClientes();
    }

    private void cargarFormularioEditar(int position) {
        Cliente cliente = (Cliente) lista.getItemAtPosition(position);
        Intent intent = new Intent(this, FormularioCliente.class);
        intent.putExtra("idCliente", cliente.getId());
        resultLauncher.launch(intent);
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

    private void mostrarClientes(){
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(
                                                this, android.R.layout.simple_list_item_1,
                                                asistenteBd.getClientes());
        lista.setAdapter(adapter);
    }

    private void cargarFormularioCrear() {
        Intent intent = new Intent(this, FormularioCliente.class);
        resultLauncher.launch(intent);
    }

    private void EstamosDeVuelta(int resultCode) {
        if(resultCode==RESULT_OK){
            mostrarClientes();
        };
    }
}