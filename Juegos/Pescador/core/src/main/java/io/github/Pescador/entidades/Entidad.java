package io.github.pescador.entidades;
import com.badlogic.gdx.math.Rectangle;

public class Entidad {
    public float ancho ;
    public float alto ;
    public float x ;
    public float y ;
    protected float velocidad ;
    public enum Estado {PARADO, ADELANTE, ATRAS};
    protected Estado estado;
    public Rectangle hitbox;

    public Entidad(float ancho, float alto, float x, float y, Estado estado , float velocidad) {
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
