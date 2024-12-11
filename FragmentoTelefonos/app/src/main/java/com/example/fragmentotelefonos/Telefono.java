package com.example.fragmentotelefonos;

public class Telefono implements onTelefonoAction {
    private String telefono;
    boolean ocupado = false;
    Operadora operadora;
    String telefonoDestino;
    onTelefonoListener listener;

    public void setListener(onTelefonoListener listener) {
        this.listener = listener;
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }

    public Telefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado() {
        this.ocupado = true;
    }

    public void setLibre() {
        this.ocupado = false;
    }

    @Override
    public String toString() {
        return telefono;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Telefono) {
            Telefono tel = (Telefono) obj;
            return this.telefono.equals(tel.getTelefono());
        }
        return false;
    }

    @Override
    public void llamar(String telefonoDestino) {
        this.telefonoDestino = telefonoDestino;
        operadora.llamar(this, telefonoDestino);
    }

    @Override
    public void colgar() {
        if (telefonoDestino != null) {
            operadora.colgar(telefonoDestino);
            telefonoDestino = null;
            listener.colgarIn();
        }
    }

    public void llamadaEstablecida() {
        this.ocupado = true;
        if (listener != null) {
            listener.recibirLlamada(telefonoDestino);
        }
    }

    public void recibirColgar() {
        if (listener != null) {
            listener.colgarIn();
        }
    }
}
