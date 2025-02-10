package io.github.disparos.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.disparos.MainGame;
import io.github.disparos.ResourceManager;
import io.github.disparos.entidades.Bala;
import io.github.disparos.entidades.Enemigo;
import io.github.disparos.entidades.Pistola;
import io.github.disparos.mundo.Mundo;

public class PantallaJuego extends Pantalla {
    public static Pistola pistola = new Pistola();
    private MainGame game ;
    private int NumEnemigos = 10;
    private static final float tiempoEntreEnemigos = 1;
    private float stateTime = 0;
    private float stateTimeProximoEnemigo = tiempoEntreEnemigos ;
    private Array<Enemigo> enemigos = new Array();

    public PantallaJuego(MainGame game) {
        super();
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Cargar recursos");
        Gdx.input.setInputProcessor(this);
        ResourceManager.cargarRecursos();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen with the specified color
        if(ResourceManager.assetsCargados()) {
            ResourceManager.asignarRecursos();
            stateTime += delta;
            SpriteBatch sb = game.getSpriteBatch();
            ShapeRenderer sr = game.getShapeRenderer();
            if(stateTime >= stateTimeProximoEnemigo){
                if(enemigos.size < NumEnemigos){
                    Enemigo enemigo = new Enemigo();
                    enemigos.add(enemigo);
                }
                stateTimeProximoEnemigo = stateTime + tiempoEntreEnemigos;
            }
            for(Enemigo enemigo : enemigos){
                enemigo.update(delta);
            }
            pistola.update(delta);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sb.begin();
            sr.setColor(0, 0, 0, 1);
            pistola.dibuja(sb,sr);
            for(Enemigo enemigo : enemigos){
                enemigo.render(sb, sr);
            }
            sb.end();
            sr.end();
        }
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        game.getSpriteBatch().setProjectionMatrix(camara.combined);
        game.getShapeRenderer().setProjectionMatrix(camara.combined);

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
        if(keycode == Input.Keys.SPACE){

        }
        return true;
    }
}
