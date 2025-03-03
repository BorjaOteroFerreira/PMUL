package io.github.examen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import io.github.examen.MainGame;
import io.github.examen.RecordManager;
import io.github.examen.ResourceManager;
import io.github.examen.entidades.Enemigo;
import io.github.examen.entidades.Entidad;
import io.github.examen.entidades.Jugador;
import io.github.examen.mundo.Mundo;

public class PantallaJuego extends Pantalla{
    private SpriteBatch sb ;
    private ShapeRenderer sr;
    private Array<Enemigo> enemigos ;
    private int numVidas;
    private float anchoJugador = 20;
    private float altoJugador = anchoJugador;
    private float altoEnemigo = 50 ;
    private float anchoEnemigo = 50;
    private float velocidadJugador = 500f;
    private float stateTime = 0f;
    private float tiempoEntreEnemigos = 3f;
    private float stateTimeProximoEnemigo = tiempoEntreEnemigos;
    private boolean crearEnemigo = true;
    boolean derecha = true;

    private static Random rnd = new Random();
    Jugador jugador ;

    public PantallaJuego(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
        jugador = new Jugador(Mundo.anchoJuego/ 2 - anchoJugador / 2, Mundo.altoJuego / 2 - altoJugador / 2, anchoJugador, altoJugador , velocidadJugador);
        enemigos = new Array<>();
        numVidas = Mundo.numColisiones;
    }

    public void Show(){
    }



    @Override
    public void render(float delta){
        ScreenUtils.clear(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen with the specified color
        comprobarColisiones();
        stateTime += delta;
        construirEnemigos();
        for (Enemigo enemigo : enemigos) {
            enemigo.update(delta);
        }
        jugador.update(delta);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        jugador.render(sr);
        sr.end();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GRAY);
        sr.rect(Mundo.xRecord, Mundo.yRecord, Mundo.anchoRecord, Mundo.altoRecord);
        sb.begin();
        for (Enemigo enemigo : enemigos) {
            enemigo.render(sr,sb);
        }
        ResourceManager.fuente.draw(sb, "Vidas: " + numVidas +  " Tiempo: "+ stateTime, Mundo.xRecord + 10, Mundo.yRecord + 20);
        sb.end();
        sr.end();
    }

    public void comprobarColisiones(){
        for(Enemigo e : enemigos){
            if(e.getHitbox().overlaps(jugador.getHitbox())){
                if(!e.tocado){
                    e.tocado = true;
                    numVidas --;
                    e.restarVida();
                }
            }
            if(numVidas <= 0){
                RecordManager.getInstance().guardarRecord("record_"+ Mundo.numColisiones, stateTime);
                stateTime = 0;
                game.cargarPantallaInicio();
                numVidas = Mundo.numColisiones;
                dispose();
            }
        }
    }

    private void construirEnemigos() {
        if (stateTime >= stateTimeProximoEnemigo) {
            crearEnemigo = true;
            if(enemigos.size > 0) {
                for (Enemigo enemigo : enemigos) {
                    if (enemigo.getEstado() == Entidad.Estado.PARADO) {
                        enemigo.setEstado(Entidad.Estado.ADELANTE);
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
            float x ;
            Enemigo nuevoEnemigo;
            x = derecha ? Mundo.anchoJuego - anchoEnemigo : 0 + anchoEnemigo  ;
                nuevoEnemigo = new Enemigo(x, rnd.nextFloat(Mundo.yJuego +anchoEnemigo, Mundo.ALTO - altoEnemigo), anchoEnemigo, altoEnemigo, 50f );
            enemigos.add(nuevoEnemigo);
            nuevoEnemigo.setEstado(Entidad.Estado.ADELANTE);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP ) {
            jugador.moverArriba();
        }
        if (keycode == Input.Keys.DOWN) {
            jugador.moverAbajo();
        }
        if(keycode == Input.Keys.P)
            game.cargarPantallaPausa();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if ((keycode == Input.Keys.UP || keycode == Input.Keys.DOWN) ) {
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                jugador.moverArriba();
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                jugador.moverAbajo();
            }else
                jugador.setEstado(Entidad.Estado.PARADO);
        }
        return true;
    }
    @Override
    public void dispose(){
        enemigos.clear();
        numVidas = Mundo.numColisiones;
        jugador.reset();
    }
}
