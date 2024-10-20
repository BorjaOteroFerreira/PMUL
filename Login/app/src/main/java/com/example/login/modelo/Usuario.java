package com.example.login.modelo;

public class Usuario extends UsuarioLogin {
    private int id;
    private String apellido;
    private String email;

    public Usuario(int id, String nombre, String apellido, String email, String password) {
        super(nombre, password);
        this.id = id;
        this.apellido = apellido;
        this.email = email;
    }

    public Usuario(String nombre, String apellido, String email, String password) {
        super(nombre, password);
        this.apellido = apellido;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return super.getNombre();
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Usuario && ((Usuario) obj).getId() == this.id;
    }

}
