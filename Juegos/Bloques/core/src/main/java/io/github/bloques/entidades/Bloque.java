package io.github.bloques.entidades;

public class Bloque extends Entidad {
    protected boolean destruido = false;

    public Bloque(float x, float y, float ancho , float alto) {
        super(x, y, ancho, alto);
    }


    public boolean isDestruido() {
        return destruido;
    }
    public void setDestruido(boolean destruido) {
        this.destruido = destruido;
    }
}
