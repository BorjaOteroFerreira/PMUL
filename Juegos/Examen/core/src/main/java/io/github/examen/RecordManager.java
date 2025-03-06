package io.github.examen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class RecordManager {
    private static RecordManager instance;
    private static final String Fichero = "record";
    private final Preferences prefs;

    private RecordManager() {
        prefs = Gdx.app.getPreferences(Fichero);
    }

    public static RecordManager getInstance() {
        if (instance == null) {
            instance = new RecordManager();
        }
        return instance;
    }

    public boolean guardarRecord(String record, float stateTimeJuego) {
        return comprobarRecord(record, stateTimeJuego);
    }

    private boolean comprobarRecord(String record, float stateTimeJuego) {
        boolean guardado = false;
        float recordGuardado = getRecord(record);
        if (stateTimeJuego > recordGuardado || recordGuardado == -1) {
            prefs.putFloat(record, stateTimeJuego);
            prefs.putFloat("utlimoRecord", stateTimeJuego);
            prefs.flush();
            guardado = true;
        }
        return guardado;
    }

    public float getRecord(String record) {
        return prefs.getFloat(record, -1);
    }

    public boolean existeRecord(String record) {
        return prefs.contains(record);
    }


    public void resetearRecords() {
        prefs.clear();
        prefs.flush();
    }

}
