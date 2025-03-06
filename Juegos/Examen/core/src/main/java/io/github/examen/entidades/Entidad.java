package io.github.examen.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entidad {
    public float x;
    public float y;
    protected float ancho, alto ;
    protected float velocidad = 1f;
    public enum Estado {PARADO, ADELANTE , ATRAS};
    public enum Tipo {CRUZ, CIRCULO, CUADRADO, TRIANGULO, ROMBO, RECTANGULO, EQUIS};
    public Tipo tipo ;
    public Estado estado;
    public Rectangle hitbox;
    protected  Texture imagen;

    public Entidad(float x, float y, float ancho, float alto, float velocidad ) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        estado = Estado.PARADO;
        hitbox = new Rectangle(x,y,ancho,alto);
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
