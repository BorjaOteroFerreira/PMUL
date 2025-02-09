package io.github.disparos.entidades;
import com.badlogic.gdx.math.Rectangle;

public class Personaje {
    protected float ancho ;
    public float alto ;
    public float x ;
    public float y ;
    protected float velocidad ;
    protected enum Estado {PARADO, ADELANTE, ATRAS};
    protected Estado estado;
    //Hitbox
    protected Rectangle hitbox;

    public Personaje(float ancho, float alto, float x, float y, Estado estado , float velocidad) {
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        this.estado = estado;
        this.velocidad = velocidad;
        hitbox = new Rectangle(x, y, ancho, alto);

    }



    public Personaje() {
        this.velocidad = 40f;
        this.estado = Estado.PARADO;

    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }



}
