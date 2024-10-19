package com.example.clientesbd.modelo;

public class Provincia {
    private int id;
    private String nombre;

    public Provincia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Provincia(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Provincia && this.id == ((Provincia) o).id;
    }
}
