package io.github.pescador.entidades;

import static io.github.pescador.entidades.Entidad.Estado.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Shape;

import io.github.pescador.ResourceManager;
import io.github.pescador.mundo.Mundo;

public class Anzuelo extends Entidad{


    public Anzuelo(float x, float y, float ancho, float alto, Estado estado, float velocidad) {
        super(ancho, alto,  x ,y , estado, velocidad);
        setEstado(PARADO);
    }

    public void update(float delta, float jugadorY,float  jugadorX) {
        if (estado == ADELANTE) {
            if (y <= jugadorY) {
                super.y = hitbox.y -= velocidad * delta;
            }
            if (y <= 0) {
                setEstado(ATRAS);
            }
        }
        if(estado == ATRAS) {
            if (y <= jugadorY) {
                super.y = hitbox.y += velocidad * delta;
            }
            else{
                setEstado(PARADO);
                super.y = hitbox.y = jugadorY;

            }
        }
    }



    public void soltarSedal () {
        setEstado(ADELANTE);
    }

}
