package com.example.fragmentotelefonos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  AsistenteBD extends SQLiteOpenHelper {

    public AsistenteBD(Context context) {
        super(context, "telefonos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE telefonos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, telefono TEXT)");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Juan', '123456789')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pedro', '987654321')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS telefonos");
        db.execSQL("CREATE TABLE telefonos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, telefono TEXT)");
    }

    public String obtenerTelefono(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String telefono = "";
        Cursor c = db.rawQuery("SELECT telefono FROM telefonos WHERE id = '" + id + "'", null);
        if (c.moveToFirst()) {
            telefono = c.getString(0);
        }
        c.close();
        return telefono;
    }

    public Cursor obtenerTelefonos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT nombre, telefono FROM telefonos", null);
    }
}

