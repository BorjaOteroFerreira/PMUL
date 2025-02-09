package io.github.disparos.entidades;

public class Bala {
    private float anchoImagen ;
    private float altoImagen;
    private float proporcionAncho = 0.1f;
    private float proporcionAlto = 0.1f;
    private float ancho;
    private float alto;
    private float x, y;
    private float velocidad;
    private boolean visible;


    public Bala() {
        this.x = x;
        this.y = y;
        this.ancho = anchoImagen * proporcionAncho;
        this.alto = altoImagen * proporcionAlto;
        this.velocidad = 4f;
    }

    public void mover(float delta) {

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
