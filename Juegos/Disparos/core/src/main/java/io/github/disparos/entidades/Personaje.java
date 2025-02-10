package io.github.disparos.entidades;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Personaje {
    public float ancho ;
    public float alto ;
    public float x ;
    public float y ;
    protected float velocidad ;
    protected enum Estado {PARADO, ADELANTE, ATRAS};
    protected Estado estado;
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


    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }


}
