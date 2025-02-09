package io.github.Pescador.Personajes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.Pescador.Assets;

class PezAnimado extends Pez {
    Animation animacion;
    float stateTimeCreacion; // para que se animen independientemente


    public PezAnimado(Animation animacion) {
        super(Assets.pez);
        this.animacion = animacion;
        stateTimeCreacion=0;
    }
    @Override
    public void dibuja(SpriteBatch batch, ShapeRenderer sr) {
        texture=((TextureRegion) animacion.getKeyFrame(stateTimeCreacion,true));
        super.dibuja(batch,sr);
    }
    @Override
    public void actualiza(float delta) {
        stateTimeCreacion+=delta;
        super.actualiza(delta);
    }
}
