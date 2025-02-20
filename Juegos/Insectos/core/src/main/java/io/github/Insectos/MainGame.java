package io.github.Insectos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import io.github.Insectos.pantallas.PantallaCarga;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private SpriteBatch sb;
    private ShapeRenderer sr;


    @Override
    public void create() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        ResourceManager.cargarRecursos();
        setScreen(new PantallaCarga(this));
    }


    @Override
    public void dispose() {
        getScreen().dispose();
        sb.dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public ShapeRenderer getShapeRenderer() {
        return sr;
    }

}
