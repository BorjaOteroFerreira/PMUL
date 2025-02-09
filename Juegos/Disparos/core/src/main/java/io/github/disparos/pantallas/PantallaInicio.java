package io.github.disparos.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.disparos.entidades.Pistola;
import io.github.disparos.pantallas.PantallaJuego;
import io.github.disparos.MainGame;


public class PantallaInicio implements Screen {
    private MainGame game;

    public PantallaInicio(MainGame game) {
        this.game = game;

    }

    @Override
    public void show() {
        Pistola pistola = new Pistola();
    }

    @Override
    public void render(float delta) {


    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
