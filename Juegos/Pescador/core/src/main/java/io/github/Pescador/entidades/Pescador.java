package io.github.pescador.entidades;

import static io.github.pescador.entidades.Entidad.Estado.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.pescador.ResourceManager;
import io.github.pescador.mundo.Mundo;

public class Pescador extends Entidad {
    float xAnzuelo ;
    float yAnzuelo ;
    Anzuelo anzuelo ;
    public Pescador(float ancho, float alto, float x, float y, Estado estado, float velocidad) {
        super(ancho, alto, x ,y, estado, velocidad);
        xAnzuelo = x + ancho - 15;
        yAnzuelo = y + alto - 30;
        anzuelo = new Anzuelo(xAnzuelo,yAnzuelo, 10, 10, PARADO, velocidad);
    }

    public void update(float delta) {
        if (super.getEstado() == ADELANTE) {
            if(x < Mundo.ANCHO - alto + alto / 2){
                x = hitbox.x += velocidad * delta;
                anzuelo.x = anzuelo.hitbox.x += velocidad * delta;
            }
        } else if (super.getEstado() == ATRAS) {
            if(x > 0) {
                x = hitbox.x -= velocidad * delta;
                anzuelo.x = anzuelo.hitbox.x -= velocidad * delta;
            }
        }

    }


    public Anzuelo getAnzuelo(){
        return anzuelo;
    }



    public void moverDerecha() {
        super.setEstado(ADELANTE);
    }
    public void moverIzquierda() {
        super.setEstado(ATRAS);
    }


    public void render(SpriteBatch sb, ShapeRenderer sr){
        //sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        sb.draw(ResourceManager.pescador, x, y, ancho, alto);
        //sr.rect(anzuelo.hitbox.x, anzuelo.hitbox.y, anzuelo.hitbox.width, anzuelo.hitbox.height);
        sb.draw(ResourceManager.anzuelo, anzuelo.x, anzuelo.y, anzuelo.ancho, anzuelo.alto);
    }





}

