package io.github.Insectos.entidades;

import com.badlogic.gdx.math.Rectangle;

public class Entidad {
    protected float x, y;
    protected float ancho, alto ;
    protected float velocidad = 1f;
    public enum Estado {PARADO, ADELANTE , ATRAS, ARRIBA, ABAJO};
    public Estado estado;
    protected Rectangle hitbox;

    public Entidad(float x, float y, float ancho, float alto, float velocidad) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        estado = Estado.PARADO;
        hitbox = new Rectangle(x,y,ancho,alto);
    }
    public void actualizar(float delta) {
    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }


}
