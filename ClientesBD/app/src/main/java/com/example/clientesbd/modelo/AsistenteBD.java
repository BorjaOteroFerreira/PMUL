package com.example.clientesbd.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE clientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellido TEXT," +
                "provincia TEXT," +
                "vip INTEGER," +
                "longitud TEXT," +
                "latitud TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        onCreate(db);
    }

    public ArrayAdapter<String> getClientes(Context context) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, nombre, apellido, provincia, vip, longitud, latitud FROM clientes";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        String[] clientes = new String[cursor.getCount()];

        for(int i = 0 ; i < cursor.getCount(); i++){
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            String provincia = cursor.getString(3);
            boolean vip = cursor.getInt(4) == 1;
            String longitud = cursor.getString(5);
            String latitud = cursor.getString(6);
            Cliente cliente = new Cliente(nombre, apellido, provincia, vip, longitud, latitud);
            clientes[i] = cliente.toString();
            cursor.moveToNext();

        }

        cursor.close();
        return new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, clientes);
    }

    public ArrayAdapter<String> getClientePorId(Context context, int idCliente) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM clientes WHERE id = " + idCliente;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String[] clientes = new String[cursor.getCount()];
        int id = cursor.getInt(0);
        String nombre = cursor.getString(1);
        String apellido = cursor.getString(2);
        String provincia = cursor.getString(3);
        boolean vip = cursor.getInt(4) == 1;
        String longitud = cursor.getString(5);
        String latitud = cursor.getString(6);
        Cliente cliente = new Cliente(id, nombre, apellido, provincia, vip, longitud, latitud);
        clientes[0] = cliente.toString();
        cursor.close();
        return new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, clientes);
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

    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS clientes");
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