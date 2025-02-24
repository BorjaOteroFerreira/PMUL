package io.github.examen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.examen.MainGame;
import io.github.examen.mundo.Mundo;

public abstract class Pantalla implements Screen, InputProcessor {
    public float ANCHO, ALTO;
    protected OrthographicCamera camera ;
    protected MainGame game;

    public Pantalla(MainGame game) {
        this.game = game;
        ANCHO = Mundo.ANCHO;
        ALTO = Mundo.ALTO;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Mundo.ANCHO, Mundo.ALTO);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, ANCHO, ALTO);
        camera.update();
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getShapeRenderer().setProjectionMatrix(camera.combined);

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }


    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
