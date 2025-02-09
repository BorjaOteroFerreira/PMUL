package io.github.disparos.entidades;
import com.badlogic.gdx.math.Rectangle;

public class Personaje {
    private float ancho ;
    private float alto ;
    private float x ;
    private float y ;
    private float velocidad ;
    protected enum Estado {PARADO, ADELANTE, ATRAS};
    private Estado estado;
    private Rectangle hitbox;

    public Personaje() {
        this.velocidad = 4f;
        this.estado = Estado.PARADO;
        this.hitbox = new Rectangle(x, y, ancho, alto);
    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
