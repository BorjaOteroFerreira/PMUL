package io.github.disparos;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.disparos.pantallas.PantallaFin;

import io.github.disparos.pantallas.Pantalla;
import io.github.disparos.pantallas.PantallaJuego;


public class MainGame extends Game {
    public SpriteBatch batch; // Compartido entre pantallas
    @Override
    public void create () {
        batch = new SpriteBatch();
        setScreen(new PantallaJuego(this));
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void dispose () {
        getScreen().dispose();
        batch.dispose();

    }
    public void setPantalla(Pantalla pantalla) {
        setScreen(pantalla);
    }
}
