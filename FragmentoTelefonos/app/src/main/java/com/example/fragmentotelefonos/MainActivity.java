package com.example.fragmentotelefonos;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    AsistenteBD asistenteBD;
    onTelefonoFragmentListener telefonoListener;
    ArrayList<Telefono> telefonos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asistenteBD = asistenteBD.getInstance(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        linearLayout = findViewById(R.id.fragment_container);
        telefonoListener = new onTelefonoFragmentListener() {
            @Override
            public Telefono obtenerTelefono(String numTelefono) {
                Telefono tel = new Telefono(numTelefono);
                int idx = telefonos.indexOf(tel);
                tel = telefonos.get(idx);
                return tel;
            }
        };
        telefonos = asistenteBD.obtenerTelefonos(asistenteBD.getReadableDatabase());
        int num_telefonos = telefonos.size();
        Operadora operadora = new Operadora(telefonos);
        for (int i = 0; i < num_telefonos; i++) {
            FragmentContainerView container = new FragmentContainerView(this);
            container.setId(View.generateViewId());
            FragmentoTelefono fragment = new FragmentoTelefono();
            Telefono tel = telefonos.get(i);
            tel.setOperadora(operadora);
            tel.setListener(fragment);
            fragment.setListener(telefonoListener , tel.getTelefono());
            getSupportFragmentManager().beginTransaction().add(container.getId(), fragment).commit();
            linearLayout.addView(container);
        }
    }
}
