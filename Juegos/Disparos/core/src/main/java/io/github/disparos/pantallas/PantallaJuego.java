package io.github.disparos.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import io.github.disparos.MainGame;
import io.github.disparos.ResourceManager;
import io.github.disparos.entidades.Bala;
import io.github.disparos.entidades.Enemigo;
import io.github.disparos.entidades.Entidad;
import io.github.disparos.entidades.Pistola;
import io.github.disparos.mundo.Mundo;

public class PantallaJuego extends Pantalla {
    public static Pistola pistola = new Pistola();
    private MainGame game;
    private int NumEnemigos = 200;
    private static final float tiempoEntreEnemigos = 1.5f;
    private float stateTime = 0;
    private float stateTimeProximoEnemigo = tiempoEntreEnemigos;
    private Array<Enemigo> enemigos = new Array();
    private int enemigosEliminados = 0;
    private Random random;

    public void renderStats() {
        ResourceManager.fuente.draw(game.getSpriteBatch(), "Enemigos Eliminados: " + enemigosEliminados, Mundo.ANCHO - 200, 10);
    }

    public PantallaJuego(MainGame game) {
        super();
        this.game = game;
        random = new Random();

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
        if (ResourceManager.assetsCargados()) {
            ResourceManager.asignarRecursos();
            stateTime += delta;
            SpriteBatch sb = game.getSpriteBatch();
            ShapeRenderer sr = game.getShapeRenderer();
            if (stateTime >= stateTimeProximoEnemigo) {
                if (enemigos.size < NumEnemigos) {
                    Enemigo enemigo = new Enemigo();
                    enemigos.add(enemigo);
                    int ran = random.nextInt(0, 10);
                    if (ran >= 5)
                        ResourceManager.segarro2Sound.play();
                    else
                        ResourceManager.segarroSound.play();
                }
                stateTimeProximoEnemigo = stateTime + tiempoEntreEnemigos;
            }
            for (Enemigo enemigo : enemigos) {
                enemigo.update(delta);
            }
            pistola.update(delta);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sb.begin();
            sb.draw(ResourceManager.background, 0, 0, Mundo.ANCHO, Mundo.ALTO);
            renderStats();
            sr.setColor(0, 0, 0, 1);
            pistola.render(sb, sr);
            for (Enemigo enemigo : enemigos) {
                enemigo.render(sb, sr);
            }
            sb.end();
            sr.end();
        }
        comprobarColisiones();
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
        if (keycode == Input.Keys.SPACE) {
            pistola.disparar();
        }
        return true;
    }

    private void comprobarColisiones() {
        for (Enemigo enemigo : enemigos) {
            for (Bala bala : pistola.getCargador()) {
                if (bala.getEstado() == Entidad.Estado.PARADO) {
                    continue;
                }
                if (bala.getHitbox().overlaps(enemigo.getHitbox())) {
                    ResourceManager.muerteSound.play();
                    enemigosEliminados++;
                    bala.reset();
                    enemigo.restarVida();
                    if (enemigo.getVidas() <= 0) {
                        enemigo.reset();
                    }
                }
            }
        }
    }
}
