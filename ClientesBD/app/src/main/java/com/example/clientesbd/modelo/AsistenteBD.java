package com.example.clientesbd.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

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

    public ArrayAdapter<String> getClientes(Context context) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM clientes";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        String[] clientes = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            String provincia = cursor.getString(3);
            boolean vip = cursor.getInt(4) == 1;
            Cliente cliente = new Cliente(id, nombre, apellido, provincia, vip);
            clientes[i] = cliente.toString();
            cursor.moveToNext();
        }
        cursor.close();
        return new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, clientes);
    }

    public ArrayAdapter<String> getClientePorId(Context context, int idCliente) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM clientes WHERE id = " + idCliente;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        String[] clientes = new String[cursor.getCount()];
        int id = cursor.getInt(0);
        String nombre = cursor.getString(1);
        String apellido = cursor.getString(2);
        String provincia = cursor.getString(3);
        boolean vip = cursor.getInt(4) == 1;
        Cliente cliente = new Cliente(id, nombre, apellido, provincia, vip);
        clientes[0] = cliente.toString();
        cursor.close();
        return new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, clientes);
    }

    public void addCliente(Cliente cliente) {
        this.getWritableDatabase().execSQL("INSERT INTO clientes " +
                "(nombre, apellido, provincia, vip) " +
                "VALUES ('" + cliente.getNombre() + "', " + "'" +
                              cliente.getApellido() + "'," + " '" +
                              cliente.getProvincia() + "', " + "" +
                         (cliente.isVip() ? 1 : 0) + ")");

    }

    public void updateCliente(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE clientes SET nombre = '" +
                cliente.getNombre() + "', " +
                "apellido = '" + cliente.getApellido() + "', " +
                "provincia = '" + cliente.getProvincia() + "', " +
                "vip = " + (cliente.isVip() ? 1 : 0) + "" +
                " WHERE id = " + cliente.getId());
    }

    public void deleteCliente(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM clientes WHERE id = " + cliente.getId());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Implement database creation here
        String sql = "CREATE TABLE clientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellido TEXT," +
                "provincia TEXT," +
                "vip INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement database upgrade here

    }
}