package com.example.clientesbd.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class AsistenteBD extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "clientes.db";
    private static final int DATABASE_VERSION = 1; 
    private static AsistenteBD instance;

    private AsistenteBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static synchronized AsistenteBD getInstance(Context context) {
        if (instance == null) {
            instance = new AsistenteBD(context.getApplicationContext());

        }
        return instance;
    }

    public void insertarProvinciasIniciales() {
        SQLiteDatabase db = getWritableDatabase();
        String countQuery = "SELECT COUNT(*) FROM provincias";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            db.execSQL("INSERT INTO provincias (nombre) VALUES ('Provincia1')");
            db.execSQL("INSERT INTO provincias (nombre) VALUES ('Provincia2')");
            db.execSQL("INSERT INTO provincias (nombre) VALUES ('Provincia3')");
            db.execSQL("INSERT INTO provincias (nombre) VALUES ('Provincia4')");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        insertarProvinciasIniciales();
        db.execSQL("CREATE TABLE clientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT UNIQUE," +
                "apellido TEXT," +
                "provincia TEXT," +
                "vip INTEGER," +
                "longitud TEXT," +
                "latitud TEXT," +
                "provincia_id INTEGER," +
                "FOREIGN KEY (provincia_id) REFERENCES provincias(id) )");

        db.execSQL("CREATE TABLE PROVINCIAS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        onCreate(db);
    }
    public ArrayList<String> getProvincias() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT nombre FROM provincias";
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<String> provincias = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                provincias.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return provincias;
    }

    public ArrayList<Cliente> getClientes(Context context) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, nombre, apellido, provincia, vip, longitud, latitud FROM clientes";
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Cliente> clientes = new ArrayList<>();
        if(cursor.moveToNext()){
         do{
             int id = cursor.getInt(0);
             String nombre = cursor.getString(1);
             String apellido = cursor.getString(2);
             String provincia = cursor.getString(3);
             boolean vip = cursor.getInt(4) == 1;
             String longitud = cursor.getString(5);
             String latitud = cursor.getString(6);
             Cliente cliente = new Cliente(id, nombre, apellido, provincia, vip, longitud, latitud);
             clientes.add(cliente);
         }while(cursor.moveToNext());
        }
        else{ System.out.println("No hay clientes"); }
        cursor.close();
        return clientes;
    }

    public Cliente getClientePorId(Context context, int idCliente) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM clientes WHERE id = " + idCliente;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String nombre = cursor.getString(1);
        String apellido = cursor.getString(2);
        String provincia = cursor.getString(3);
        boolean vip = cursor.getInt(4) == 1;
        String longitud = cursor.getString(5);
        String latitud = cursor.getString(6);
        Cliente cliente = new Cliente(id, nombre, apellido, provincia, vip, longitud, latitud);
        cursor.close();
        return cliente;
    }

    public void addCliente(Cliente cliente) {
        this.getWritableDatabase().execSQL("INSERT INTO clientes " +
                "(nombre, apellido, provincia, vip, longitud, latitud) " +
                "VALUES ('" + cliente.getNombre() + "', '" +
                cliente.getApellido() + "', '" +
                cliente.getProvincia() + "', " +
                cliente.isVip() + ", '" +
                cliente.getLongitud() + "', '" +
                cliente.getLatitud() + "')");
    }

    public void updateCliente(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE clientes SET nombre = '" +
                cliente.getNombre() + "', " +
                "apellido = '" + cliente.getApellido() + "', " +
                "provincia = '" + cliente.getProvincia() + "', " +
                "vip = " + cliente.isVip() + ", " +
                "longitud = '" + cliente.getLongitud() + "', " +
                "latitud = '" + cliente.getLatitud() + "' " +
                "WHERE id = " + cliente.getId());
    }

    public void createTable() {
        SQLiteDatabase db = getWritableDatabase();
        onCreate(db);
    }

    public void deleteCliente(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM clientes WHERE id = " + cliente.getId());
    }
}