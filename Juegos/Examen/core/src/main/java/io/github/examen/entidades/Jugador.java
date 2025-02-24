package io.github.examen.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.examen.mundo.Mundo;

public class Jugador extends Entidad {
    float tamMax = 200;
    float tamMin = ancho;
    float aumento = 2 ;
    boolean aumentar = true;

    public Jugador(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
    }

    public void render(ShapeRenderer sr){
        sr.rect(x, y, ancho, alto);
    }

    public void moverArriba() {
        super.setEstado(Estado.ADELANTE);
    }
    public void moverAbajo() {
        super.setEstado(Estado.ATRAS);
    }


    public void update(float delta) {
        if (super.getEstado() == Estado.ADELANTE) {
            if(y < Mundo.ALTO - alto)
                y += velocidad * delta;
        } else if (super.getEstado() == Estado.ATRAS) {
            if(y > 0 ){
                y -= velocidad * delta;
            }
        }
    }

    public void reset(){
        y = Mundo.ALTO / 2 ;
        x = Mundo.ANCHO / 2;
        super.estado = Estado.PARADO;
    }
}
