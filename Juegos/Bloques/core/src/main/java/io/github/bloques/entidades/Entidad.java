package io.github.bloques.entidades;

import com.badlogic.gdx.math.Rectangle;

public class Entidad {
    public float x;
    public float y;
    public float ancho;
    public float alto;
    public Rectangle hitbox ;
    public


     Entidad(float x, float y, float ancho, float alto) {
        this.x =  x;
        this.y =  y;
        this.ancho = ancho;
        this.alto = alto;
        hitbox = new Rectangle(x, y, ancho, alto);

    }

}
