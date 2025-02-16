package io.github.bloques.entidades;

import com.badlogic.gdx.math.Rectangle;

public class Entidad {
    protected float x;
    protected float y;
    protected float ancho;
    protected float alto;
    public Rectangle hitbox ;
    public Entidad(float x, float y, float ancho, float alto) {
        this.x = hitbox.x = x;
        this.y = hitbox.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }
}
