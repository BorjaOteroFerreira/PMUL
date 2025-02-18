package io.github.Insectos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.Insectos.pantallas.PantallaInicio;

public class MainGame extends Game {
    SpriteBatch sb ;
    ShapeRenderer sr;

    public void create() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        setScreen(new PantallaInicio(this));

    }

    public SpriteBatch getSpriteBatch(){
        return sb;
    }
    public ShapeRenderer getShaperenderer(){
        return sr;
    }



}
