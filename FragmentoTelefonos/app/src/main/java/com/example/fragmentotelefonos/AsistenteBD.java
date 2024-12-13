package com.example.fragmentotelefonos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class  AsistenteBD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "telefonos.db";
    private static final int DATABASE_VERSION = 1;
    private static AsistenteBD instance;

    private AsistenteBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized AsistenteBD getInstance(Context context) {
        if (instance == null) {
            instance = new AsistenteBD(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE telefonos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, telefono TEXT)");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Juan', '11')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pedro', '22')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Mariano', '33')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Maria', '44')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pepe', '55')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pepa', '66')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Juancho', '77')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pedrosa', '88')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Juanito', '99')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pedrito', '1010')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Juanito', '1111')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pedrito', '1212')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS telefonos");
        db.execSQL("CREATE TABLE telefonos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, telefono TEXT)");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Juan', '11')");
        db.execSQL("INSERT INTO telefonos (nombre, telefono) VALUES ('Pedro', '22')");

    }

    public ArrayList<Telefono> obtenerTelefonos(SQLiteDatabase db) {
        ArrayList<Telefono> listaTelefonos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nombre, telefono FROM telefonos", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));
                listaTelefonos.add(new Telefono(telefono));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listaTelefonos;
    }
}

