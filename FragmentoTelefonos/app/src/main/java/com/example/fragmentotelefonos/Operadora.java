package com.example.fragmentotelefonos;

import java.util.ArrayList;

public class Operadora {
     ArrayList<Telefono> telefonos;

    private  boolean comprobarTelefono(Telefono telefono){
        for (Telefono tel : telefonos) {
            if (tel.equals(telefono)) {
                return true;
            }
        }
        return false;
    }

    public Operadora(ArrayList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public void llamar(Telefono telefonoOrigen , String telDestino){
        Telefono telefonoDestino = new Telefono(telDestino);
        int idx = telefonos.indexOf(telefonoDestino);
        telefonoDestino = telefonos.get(idx);
        if (comprobarTelefono(telefonoDestino)) {
            if(!telefonoDestino.isOcupado()) {
                System.out.println(telefonoOrigen + " Llamando a " + telefonoDestino);
                telefonoDestino.llamadaEstablecida();
                telefonoOrigen.llamadaEstablecida();
                telefonoOrigen.setOcupado();
                telefonoDestino.setOcupado();
            }
            else{
                System.out.println("El telefono destino esta ocupado");
            }
        } else {
            System.out.println("Llamando a numero externo");
            telefonoOrigen.setOcupado();
        }
    }

    public void colgar(String telfDestino){
        Telefono telefonoDestino = new Telefono(telfDestino);
        int idx = telefonos.indexOf(telefonoDestino);
        telefonoDestino = telefonos.get(idx);
        telefonoDestino.setLibre();
        telefonoDestino.recibirColgar();
        System.out.println("Colgando a " + telefonoDestino);
    }
}

