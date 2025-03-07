package io.github.pescador.pantallas;

import static io.github.pescador.entidades.Entidad.Estado.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Shape;

import java.util.Random;

import io.github.pescador.MainGame;
import io.github.pescador.ResourceManager;
import io.github.pescador.entidades.Entidad;
import io.github.pescador.entidades.Pescador;
import io.github.pescador.entidades.Pez;
import io.github.pescador.mundo.Mundo;


public class PantallaJuego extends Pantalla {
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private boolean recursosAsignados = false;
    Array<Pez> peces = new Array<>();
    private Pescador jugador;
    private float stateTime = 0;
    private float stateTimeProximoEnemigo = 0;
    private float tiempoEntreEnemigos = 1f;
    private boolean crearEnemigo = false;
    private Random rnd = new Random();
    public float anchoEnemigo = 75;
    public float altoEnemigo = 75;


    public PantallaJuego(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
        ResourceManager.cargarRecursos();
    }

    @Override
    public void show() {
        super.show();
        if (ResourceManager.assetsCargados()) {
            ResourceManager.asignarRecursos();
            recursosAsignados = true;
            jugador = new Pescador(150, 100, Mundo.ANCHO / 2, (Mundo.ALTO / 3) * 2 -10, PARADO, 200);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;
        construirEnemigos();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        sb.draw(ResourceManager.background, 0, 0, Mundo.ANCHO, Mundo.ALTO);
        jugador.update(delta);
        for (Pez pez : peces) {
            pez.update(delta);
            pez.render(sb, sr);
        }
        jugador.getAnzuelo().update(delta, jugador.y + jugador.alto - 30, jugador.x);
        jugador.render(sb, sr);
        sb.end();
        sr.end();
    }

    private void construirEnemigos() {
        if (stateTime >= stateTimeProximoEnemigo) {
            crearEnemigo = true;
            if(peces.size > 0) {
                for (Pez pez : peces) {
                    if (pez.getEstado() == Entidad.Estado.PARADO) {
                        boolean salePorDerecha = rnd.nextBoolean();
                        if (salePorDerecha) {
                            pez.x = pez.hitbox.x = Mundo.ANCHO - anchoEnemigo; // Sale por la derecha
                            pez.setEstado(Entidad.Estado.ATRAS);
                        }else{
                            pez.x = pez.hitbox.x = 0; // Sale por la izquierda
                            pez.setEstado(Entidad.Estado.ADELANTE); // Se mueve hacia la derecha
                        }
                        pez.y = pez.hitbox.y = 0 + rnd.nextFloat() * ((Mundo.ALTO / 3) * 2 - altoEnemigo);
                        crearEnemigo = false;
                        break;
                    }
                }
            }
            if (crearEnemigo) {
                crearEnemigo();
            }
            stateTimeProximoEnemigo = stateTime + tiempoEntreEnemigos;
        }
    }
    private void crearEnemigo() {
        float x;
        Pez nuevoEnemigo;
        boolean salePorDerecha = rnd.nextBoolean();
        float posY = 0 + rnd.nextFloat() * ((Mundo.ALTO / 3) * 2 - altoEnemigo);
        if (salePorDerecha) {
            x = Mundo.ANCHO - anchoEnemigo; // Sale por la derecha
            nuevoEnemigo = new Pez( anchoEnemigo, altoEnemigo,x, posY, PARADO,rnd.nextFloat() * 100 + 50);
            nuevoEnemigo.setEstado(Entidad.Estado.ATRAS); // Se mueve hacia la izquierda
        } else {
            x = 0; // Sale por la izquierda
            nuevoEnemigo = new Pez( anchoEnemigo, altoEnemigo,x, posY,PARADO, rnd.nextFloat() * 100 + 50);
            nuevoEnemigo.setEstado(Entidad.Estado.ADELANTE); // Se mueve hacia la derecha
        }
        peces.add(nuevoEnemigo);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.RIGHT && jugador.getAnzuelo().getEstado() == PARADO) {
            jugador.moverDerecha();
        }
        if (keycode == Input.Keys.LEFT && jugador.getAnzuelo().getEstado() == PARADO) {
            jugador.moverIzquierda();
        }
        if (keycode == Input.Keys.SPACE && jugador.getEstado() == PARADO && jugador.getAnzuelo().getEstado() == PARADO) {
            jugador.getAnzuelo().soltarSedal();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if ((keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) ) {
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && jugador.getAnzuelo().getEstado() == PARADO){
                jugador.moverDerecha();
            }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && jugador.getAnzuelo().getEstado() == PARADO){
                jugador.moverIzquierda();
            }else{
                jugador.setEstado(PARADO);

            }
        }
        return true;
    }


    @Override
    public void dispose() {
    }

}


