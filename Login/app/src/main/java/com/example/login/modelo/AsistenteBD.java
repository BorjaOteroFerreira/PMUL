package com.example.login.modelo;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class AsistenteBD extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usaurios.db";
    private static final int DATABASE_VERSION = 4;
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

    public ArrayList<String> getUsuarioYPass(String usuario) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<String> datos = new ArrayList<>();
        String[] campos = new String[]{"nombre", "password","salt"};
        String where = "nombre = ? ";
        String[] args = new String[]{usuario};
        Cursor cursor = db.query("usuarios", campos, where, args, null, null,
                                                                        null, null);
        if (cursor.moveToFirst()) {
            do {
                datos.add(cursor.getString(0));
                datos.add(cursor.getString(1));
                datos.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return datos;
    }

    public void registrarUsuario(String nombre, String apellido, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        // Generar un salt único para el usuario
        String salt = PasswordHasher.generarSalt();  // Método para generar un salt aleatorio
        // Hashear la contraseña con el salt
        String hashedPassword = PasswordHasher.hashPassword(password, salt);
        // Guardar el usuario en la base de datos (nombre, apellido, email, hashedPassword y salt)
        db.execSQL("INSERT INTO usuarios (nombre, apellido, email, password, salt) VALUES ('"
                + nombre + "', '"
                + apellido + "', '"
                + email + "', '"
                + hashedPassword + "', '"
                + salt + "')");
    }

    public void insertarUsuarioInicial() {
        SQLiteDatabase db = getWritableDatabase();
        String countQuery = "SELECT COUNT(*) FROM usuarios";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if (count == 0) {
            String salt = PasswordHasher.generarSalt();
            String pass = PasswordHasher.hashPassword("admin123",salt);

            db.execSQL("INSERT INTO usuarios (nombre, " +
                    "apellido, " +
                    "email, " +
                    "password) VALUES ('admin', " +
                    "'adminApellido', " +
                    "'admin@admin', '" + pass + "',  + '"+ salt + "'+)");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT UNIQUE NOT NULL," +
                "apellido TEXT," +
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "salt TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}

