package com.example.login.modelo;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class AsistenteBD extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usaurios.db";
    private static final int DATABASE_VERSION = 2;
    private static  AsistenteBD instance = null;

    public AsistenteBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized AsistenteBD getInstance(Context context) {
        if (instance == null) {
            instance = new AsistenteBD(context);
        }
        return instance;
    }

    public ArrayList<String> getUsuarioYcontraseña(String usuario, String contraseña) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<String> datos = new ArrayList<>();
        String[] args = new String[]{usuario, contraseña};
        String[] campos = new String[]{"nombre", "password"};
        String where = "nombre = ? AND password = ?";
        Cursor cursor = db.query("usuarios", campos, where, args, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                datos.add(cursor.getString(0));
                datos.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return datos;
    }

    public void insertarUsuarioInicial() {
        SQLiteDatabase db = getWritableDatabase();
        String countQuery = "SELECT COUNT(*) FROM usuarios";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if (count == 0) {
            db.execSQL("INSERT INTO usuarios (nombre, apellido, email, password) VALUES ('admin', 'adminApellido', 'admin@admin', 'admin123')");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT UNIQUE NOT NULL," +
                "apellido TEXT," +
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}

