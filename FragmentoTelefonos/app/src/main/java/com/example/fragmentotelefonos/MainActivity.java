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

public class MainActivity extends AppCompatActivity implements onTelefonoListener {
    LinearLayout linearLayout;
    AsistenteBD asistenteBD;
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
        int num_telefonos = asistenteBD.obtenerTelefonos().getCount();

        for (int i = 0; i < num_telefonos; i++) {
            FragmentContainerView container = new FragmentContainerView(this);
            container.setId(View.generateViewId());
            FragmentoTelefono fragment = new FragmentoTelefono();
            int finalI = i;
            fragment.setListener(this , finalI + 1);
            getSupportFragmentManager().beginTransaction().add(container.getId(), fragment).commit();
            linearLayout.addView(container);
        }
    }

    @Override
    public String obtenerTelefono(int id) {
        return asistenteBD.obtenerTelefono(id);
    }
}
