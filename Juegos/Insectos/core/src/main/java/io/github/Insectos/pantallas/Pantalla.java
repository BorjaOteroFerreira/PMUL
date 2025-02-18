package io.github.Insectos.pantallas;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import io.github.Insectos.MainGame;
import io.github.Insectos.Mundo.Mundo;

public class Pantalla implements Screen, InputProcessor {
    public float ancho , alto ;
    protected OrthographicCamera camara;
    protected MainGame game;

    public Pantalla(MainGame game){
        this.game = game;
        this.ancho = Mundo.ANCHO;
        this.alto = Mundo.ALTO ;
        camara = new OrthographicCamera(ancho , alto);

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        camara.setToOrtho(false , ancho , alto);
        camara.update();
        game.getSpriteBatch().setProjectionMatrix(camara.combined);
        game.getShaperenderer().setProjectionMatrix(camara.combined);
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
    public void show() {

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
