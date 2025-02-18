package io.github.Insectos.pantallas;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import io.github.Insectos.MainGame;

public class PantallaInicio extends Pantalla{
    SpriteBatch sb;
    ShapeRenderer sr;

    public PantallaInicio(MainGame game) {
        super(game);
    }


    public void render(float delta){
        sb = game.getSpriteBatch();
        sr = game.getShaperenderer();
        sb.begin();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(0, 0, 100, 100);
        sb.end();
        sr.end();

    }
}
