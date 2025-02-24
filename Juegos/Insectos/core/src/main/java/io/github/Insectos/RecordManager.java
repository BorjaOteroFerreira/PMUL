package io.github.Insectos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class RecordManager {
    private static RecordManager instance;
    private static final String RECORD_FILE = "record";
    private final Preferences prefs;

    private RecordManager() {
        prefs = Gdx.app.getPreferences(RECORD_FILE);
    }

    public static RecordManager getInstance() {
        if (instance == null) {
            instance = new RecordManager();
        }
        return instance;
    }

    public boolean saveRecord(String record, float stateTimeJuego) {
        return comprobarRecord(record, stateTimeJuego);
    }

    private boolean comprobarRecord(String record, float stateTimeJuego) {
        boolean guardado = false;
        float recordGuardado = getRecord(record);
        if (stateTimeJuego < recordGuardado || recordGuardado == -1) {
            prefs.putFloat(record, stateTimeJuego);
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


    public void resetAllRecords() {
        prefs.clear();
        prefs.flush();
    }

}
