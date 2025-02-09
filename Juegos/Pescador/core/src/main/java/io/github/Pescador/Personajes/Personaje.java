package io.github.Pescador.Personajes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public abstract class Personaje {
    enum Estado {PARADO,ADELANTE,ATRAS};
    protected Estado estado;
    protected Rectangle hitBox;
    protected float x, y;
    protected float ancho, alto;
    protected int velocidad;
    public Personaje(float x,float y,float ancho,float alto,int velocidad, Estado estado) {
        this.x=x; this.y=y; this.alto=alto; this.ancho=ancho;
        this.velocidad=velocidad;
        this.estado=estado;
        hitBox=new Rectangle(x,y,ancho,alto);
    }
    abstract public void actualiza(float delta);
    abstract public void dibuja(SpriteBatch sb, ShapeRenderer sr);
    public boolean estaParado() { return estado==Estado.PARADO; }
    public void para() { estado=Estado.PARADO; }
    public void adelante() { estado=Estado.ADELANTE; }
    public void atras() { estado=Estado.ATRAS; }
    public Rectangle getHitBox() { return hitBox; }
}
