package io.github.disparos.pantallas;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.disparos.MainGame;
import io.github.disparos.mundo.Mundo;

public class Pantalla implements Screen, InputProcessor {
    private boolean debug = false;
    protected float ANCHO ;
    protected float ALTO ;
    protected OrthographicCamera   camara;
    protected Viewport vista;
    protected MainGame game;

    public Pantalla(MainGame game) {
        this.game = game;
        ANCHO = Mundo.ANCHO;
        ALTO = Mundo.ALTO;
        camara = new OrthographicCamera();
        //camara.position.set(ANCHO / 2, ALTO / 2, 0);

        //vista = new FitViewport(ANCHO, ALTO, camara);

    }

    @Override public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void resize(int width, int height) {
        //vista.update(width, height);
        //camara.position.set(camara.viewportWidth / 2, camara.viewportHeight / 2, 0);
        camara.setToOrtho(false, Mundo.ANCHO,Mundo.ALTO);
        camara.update();
        game.getSpriteBatch().setProjectionMatrix(camara.combined);
        game.getShapeRenderer().setProjectionMatrix(camara.combined);

    }
    @Override public void dispose() {


    }
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button){return false;}
}

