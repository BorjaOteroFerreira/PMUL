package io.github.disparos.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.disparos.MainGame;
import io.github.disparos.ResourceManager;
import io.github.disparos.entidades.Pistola;

public class PantallaJuego extends Pantalla {
    private Pistola pistola ;
    private MainGame game ;


    public PantallaJuego(MainGame game) {
        super();
        this.game = game;
        this.pistola = new Pistola();
    }

    @Override
    public void show() {
        System.out.println("Cargar recursos");
        ResourceManager.cargarRecursos();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Set background color to white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen with the specified color

        if(ResourceManager.assetsCargados()) {
            ResourceManager.asignarRecursos();
            SpriteBatch sb = game.getSpriteBatch();
            sb.begin();
            pistola.dibuja(sb);
            pistola.actualizar(delta);
            sb.end();
        }
    }
    @Override
    public void resize(int width, int height) {
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            pistola.moverArriba();
        }
        if (keycode == Input.Keys.DOWN) {
            pistola.moverAbajo();
        }
        return true;
    }
}
