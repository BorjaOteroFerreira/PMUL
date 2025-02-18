package io.github.Insectos.entidades;

import com.badlogic.gdx.math.Rectangle;

public class Entidad {
    public float x , y , ANCHO , ALTO ;
    public Rectangle hitbox;

    public Entidad(float x , float y , float ANCHO , float ALTO){
        this.x = x;
        this.y = y;
        this.ANCHO = ANCHO;
        this.ALTO = ALTO;
        hitbox = new Rectangle(x , y , ANCHO , ALTO);

    }


}
