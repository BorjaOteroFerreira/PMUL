package com.example.perfil;
import android.content.SharedPreferences;
import android.content.Context;

public class Ajustes {

    private final SharedPreferences sp;
    private static Ajustes instancia;
    private final String CLAVE_NOMBRE = "nombre";
    private final String CLAVE_EDAD = "edad";
    private final String CLAVE_CASADO = "casado";
    private String nombre;
    private int edad;
    private boolean casado = false ;

    private Ajustes(Context c) {
            this.sp = c.getSharedPreferences("sp.xml", c.MODE_PRIVATE);
            cargar();
    }

    public static Ajustes getInstance(Context c){
        if(instancia == null ){
            instancia = new Ajustes(c);
        }
        return instancia;
    }

    public String getNombre() {
        return !nombre.isEmpty() ? nombre : "amig@";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = Math.max(edad, 0);
    }

    public boolean getCasado() {
        return casado;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

    public void guardar(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CLAVE_NOMBRE, nombre)
                .putInt(CLAVE_EDAD, edad)
                .putBoolean(CLAVE_CASADO, casado)
                .apply();
    }

    private void cargar(){
        nombre = sp.getString("nombre", "");
        edad = sp.getInt("edad", 0);
        casado = sp.getBoolean("casado", false);
    }

}
