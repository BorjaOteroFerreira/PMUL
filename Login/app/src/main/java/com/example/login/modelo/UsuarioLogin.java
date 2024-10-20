package com.example.login.modelo;

public class UsuarioLogin {

    private String nombre;
    private String password;

    public UsuarioLogin(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        //lambda
        return obj instanceof UsuarioLogin &&
                ((UsuarioLogin) obj).getNombre().equals(this.nombre) &&
                ((UsuarioLogin) obj).getPassword().equals(this.password);
    }
}
