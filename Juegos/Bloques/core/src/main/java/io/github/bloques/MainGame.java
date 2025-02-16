package io.github.bloques;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.bloques.pantallas.PantallaJuego;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private static SpriteBatch batch;
    private static ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        setScreen(new PantallaJuego(this)); // Add this line

    }

    public static SpriteBatch getSpriteBatch() {
        return batch;
    }
    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
