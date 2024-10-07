package com.example.perfil;
import android.content.SharedPreferences;
import android.content.Context;

public class Ajustes {

    private SharedPreferences sp;
    private Context c ;
    private static Ajustes instancia;
    private final String CLAVE_NOMBRE = "nombre";
    private final String CLAVE_EDAD = "edad";
    private final String CLAVE_CASADO = "casado";
    private String nombre;
    private int edad;
    private boolean casado = false ;


    //PATRON SINGLETON
    private Ajustes(Context c) {
            this.c = c;
            this.sp = c.getSharedPreferences("sp.xml", c.MODE_PRIVATE);
            cargarDatos();
    }

    public static Ajustes getInstance(Context c){
        if(instancia == null ){
            instancia = new Ajustes(c);
        }
        return instancia;
    }

    public String getNombre() {
        return !nombre.equals("") ?  nombre : "amig@";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad > 0 ? edad : 0 ;
    }

    public boolean getCasado() {
        return casado;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

    public void guardarDatos(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CLAVE_NOMBRE, nombre)
                .putInt(CLAVE_EDAD, edad)
                .putBoolean(CLAVE_CASADO, casado).apply();
    }

    public void cargarDatos(){
        nombre = sp.getString("nombre", "");
        edad = sp.getInt("edad", 0);
        casado = sp.getBoolean("casado", false);
    }

}
