package io.github.Insectos.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Insecto extends Entidad {


    public Insecto(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
    }


    public void update(float delta) {
        super.actualizar(delta);
    }

    public void render(SpriteBatch sb) {
        return;
    }


}
