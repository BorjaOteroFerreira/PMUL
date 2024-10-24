package com.example.clientesbd.modelo;

public class Cliente {
    private int id  = -1;
    private String nombre;
    private String apellido;
    private Provincia provincia;
    private boolean vip;
    private String longitud;
    private String latitud;

    public Cliente(int id , String nombre, String apellido, Provincia provincia, boolean vip,
                                                                              String longitud,
                   String latitud) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.provincia = provincia;
        this.vip = vip;
        this.longitud = longitud;
        this.latitud = latitud;
    }
    public Cliente(int id , String nombre, String apellido,  boolean vip,
                   String longitud,
                   String latitud) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.vip = vip;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Cliente(String nombre, String apellido, Provincia provincia, boolean vip, String longitud,
                                                                                  String latitud) {
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
    public Provincia getProvincia() {
        return provincia;
    }
    public int isVip() {
        return (vip) ? 1 : 0;
    }
    public int getId() {
        return id;

    }
    public String getLongitud() {
        return longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return id + " " + nombre + " " + apellido + " (" + provincia + ")";
    }
}
