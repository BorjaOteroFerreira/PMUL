package io.github.pescador.entidades;

import static io.github.pescador.entidades.Entidad.Estado.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.pescador.ResourceManager;
import io.github.pescador.mundo.Mundo;

public class Pez extends Entidad {
    private float stateTime = 1f;
    public Pez(float ancho, float alto, float x, float y, Estado estado, float velocidad) {
        super(ancho, alto, x, y, estado, velocidad);
    }

    public void update(float delta) {
        stateTime += delta;
        if (super.getEstado() == ADELANTE) {
            if (x < Mundo.ANCHO - ancho) {
                x = hitbox.x += velocidad * delta;
            }else{
                reset();
            }
        } else if (super.getEstado() == ATRAS) {

            if (x > 0 - ancho) {
                x = hitbox.x -= velocidad * delta;
            }else{
                reset();
            }
        }

    }

    public void render(SpriteBatch sb, ShapeRenderer sr) {
        TextureRegion frame = ResourceManager.animacionLila.getKeyFrame(stateTime, true);
        if(estado == ADELANTE) {
            sb.draw(frame, x + ancho, y, -ancho , alto);
        }
        if(estado == ATRAS) {
            sb.draw(frame, x, y, ancho, alto);
        }
    }

    public void reset(){
        x = 0;
        y = 0;
        estado = PARADO;
        stateTime = 0;
    }

}
