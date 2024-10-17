package com.example.clientesbd.modelo;

public class Cliente {
    private String nombre;
    private String apellido;
    private String provincia;
    private boolean vip;

    public Cliente(String nombre, String apellido, String provincia, boolean vip) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.provincia = provincia;
        this.vip = vip;
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
}
