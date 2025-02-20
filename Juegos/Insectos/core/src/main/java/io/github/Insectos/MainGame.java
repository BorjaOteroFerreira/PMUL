package io.github.Insectos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;
import java.awt.Shape;

import io.github.Insectos.pantallas.PantallaInicio;
import io.github.Insectos.pantallas.PantallaJuego;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private SpriteBatch sb;
    private ShapeRenderer sr;


    @Override
    public void create() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        ResourceManager.asignarRecursos();
<<<<<<< Updated upstream
        setScreen(new PantallaJuego(this));
=======
        setScreen(new PantallaInicio(this));
>>>>>>> Stashed changes
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
