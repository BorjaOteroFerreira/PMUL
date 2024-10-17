package com.example.clientesbd.modelo;

public class Cliente {
    private int id  = -1;
    private String nombre;
    private String apellido;
    private String provincia;
    private boolean vip;
    private String longitud;
    private String latitud;

    public Cliente(int id , String nombre, String apellido, String provincia, boolean vip, String longitud, String latitud) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.provincia = provincia;
        this.vip = vip;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Cliente(String nombre, String apellido, String provincia, boolean vip, String longitud, String latitud) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.provincia = provincia;
        this.vip = vip;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getProvincia() {
        return provincia;
    }

    public boolean isVip() {
        return vip;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + provincia + ")";
    }
}
