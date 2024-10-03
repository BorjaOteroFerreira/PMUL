package com.example.tarifasluz;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTramo, textViewTiempoRestante;
    private Handler handler = new Handler();
    private Runnable actualizarDatosRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewTramo = findViewById(R.id.textViewTramo);
        textViewTiempoRestante = findViewById(R.id.textViewRestante);

        // Runnable que actualiza los datos cada minuto
        actualizarDatosRunnable = new Runnable() {
            @Override
            public void run() {
                actualizarDatos();
                handler.postDelayed(this, TimeUnit.MINUTES.toMillis(1)); // Actualizar cada minuto
            }
        };
        handler.post(actualizarDatosRunnable); // Iniciar la actualización
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(actualizarDatosRunnable); // Parar el handler cuando se destruye la actividad
    }

    /**
     * Actualiza los datos de la interfaz de usuario con la información actualizada.
     *
     */
    private void actualizarDatos() {
        // Obtener la hora actual
        Calendar calendario = Calendar.getInstance();
        int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
        int minutoActual = calendario.get(Calendar.MINUTE);

        // Determinar en qué tramo horario estamos y cuál es el siguiente
        String[] tramoYProximo = obtenerTramoYProximoTramo(horaActual, minutoActual);
        textViewTramo.setText("Estamos en el tramo: " + tramoYProximo[0]);

        // Calcular el tiempo restante hasta el próximo tramo
        long tiempoRestante = obtenerTiempoHastaProximoTramo(horaActual, minutoActual);
        String tiempoFormateado = formatearTiempoRestante(tiempoRestante);
        textViewTiempoRestante.setText(tramoYProximo[1] +" en " + tiempoFormateado );
    }

    /**
     * Determina en qué tramo horario estamos y cuál es el siguiente.
     *
     * @param hora   el valor de la hora actual (0-23).
     * @param minuto el valor del minuto actual (0-59).
     * @return un array de dos elementos con el tramo actual y el siguiente.
     */
    private String[] obtenerTramoYProximoTramo(int hora, int minuto) {
        if ((hora >= 0 && hora < 8) || (hora == 23 && minuto >= 0)) {
            return new String[]{"Valle", "Llano"};
        } else if ((hora >= 8 && hora < 10)) {
            return new String[]{"Llano", "Punta"};
        } else if (hora >= 10 && hora < 14) {
            return new String[]{"Punta", "Llano"};
        } else if (hora >= 14 && hora < 18) {
            return new String[]{"Llano", "Punta"};
        } else if (hora >= 18 && hora < 22) {
            return new String[]{"Punta", "Llano"};
        } else if (hora >= 22 && hora < 24) {
            return new String[]{"Llano", "Valle"};
        }
        return new String[]{"Desconocido", "Desconocido"};
    }

    /**
     *
     * Calcula el tiempo restante hasta el próximo tramo horario
     *
     *
     * @param hora   el valor de la hora actual (0-23).
     * @param minuto el valor del minuto actual (0-59).
     * @return el tiempo restante en minutos hasta el próximo tramo horario.
     */
    private long obtenerTiempoHastaProximoTramo(int hora, int minuto) {
        // Definir los cambios de tramo en minutos desde la medianoche
        int[] cambiosDeTramo = {0, 480, 600, 840, 1080, 1320, 1440}; // (00:00, 08:00, 10:00, 14:00, 18:00, 22:00)
        // Convertir la hora actual en minutos desde la medianoche
        int minutosActuales = hora * 60 + minuto;

        // Encontrar el siguiente cambio de tramo
        for (int cambio : cambiosDeTramo) {
            if (minutosActuales < cambio) {
                return cambio - minutosActuales;
            }
        }
        // Si no hay más cambios en el día, volvemos al primer tramo del siguiente día (00:00)
        return (1440 - minutosActuales); // 1440 son los minutos en 24 horas
    }

    /**
     * Formatea el tiempo restante en horas y minutos.
     *
     * @param minutosRestantes Tiempo restante en minutos.
     * @return String con el tiempo formateado en horas y minutos.
     */
    private String formatearTiempoRestante(long minutosRestantes) {
        long horas = TimeUnit.MINUTES.toHours(minutosRestantes);
        long minutos = minutosRestantes - TimeUnit.HOURS.toMinutes(horas);
        return String.format(Locale.getDefault(), "%02d h %02d m", horas, minutos);
    }
}
