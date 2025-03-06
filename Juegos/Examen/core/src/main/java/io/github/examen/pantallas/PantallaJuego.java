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
    private float altoEnemigo = 50;
    private float anchoEnemigo = 50;
    private float velocidadJugador = 500f;
    private float stateTime = 0f;
    private float tiempoEntreEnemigos = 3f;
    private float stateTimeProximoEnemigo = tiempoEntreEnemigos;
    private boolean crearEnemigo = true;
    private boolean pause = false;

    private static Random rnd = new Random();
    Jugador jugador ;

    public PantallaJuego(MainGame game) {
        super(game);
        enemigos = new Array<>();
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
        jugador = new Jugador(Mundo.anchoJuego/ 2 - anchoJugador / 2, Mundo.altoJuego / 2 - altoJugador / 2, anchoJugador, altoJugador , velocidadJugador);
    }


    @Override
    public void show(){
        super.show();
        if(!pause){
            numVidas = Mundo.numColisiones;
            stateTime = 0;
            stateTimeProximoEnemigo = tiempoEntreEnemigos;
            crearEnemigo = true;
        }else{
            pause = false;
        }
    }
    @Override
    public void render(float delta){
        ResourceManager.fuente.getData().setScale(1f);
        ScreenUtils.clear(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen with the specified color
        comprobarColisiones();
        stateTime += delta;
        construirEnemigos();
        for (Enemigo enemigo : enemigos) {
            enemigo.update(delta);
        }
        jugador.update(delta);
        sr.begin(ShapeRenderer.ShapeType.Line);
        jugador.render(sr);
        sr.end();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GRAY);
        sr.rect(Mundo.xRecord, Mundo.yRecord, Mundo.anchoRecord, Mundo.altoRecord);
        sb.begin();
        for (Enemigo enemigo : enemigos) {
            enemigo.render(sr,sb);
        }
        int colisiones  = Mundo.numColisiones - numVidas;
        ResourceManager.fuente.getData().setScale(2f);
        ResourceManager.fuente.draw(sb,
            "Vidas: " + numVidas +" Colisiones: " + colisiones +  " Tiempo: "+ (int)stateTime,
            Mundo.xRecord + 10, Mundo.yRecord + 75);
        sb.end();
        sr.end();
    }

    public void comprobarColisiones(){
        for(Enemigo e : enemigos){
            if(e.getHitbox().overlaps(jugador.getHitbox()) ){
                if(!e.tocado){
                    //e.restarVida();
                    e.tocado = true;
                    if(e.tipo != jugador.tipo){
                        numVidas --;
                    }
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
                        boolean salePorDerecha = rnd.nextBoolean();
                        if (salePorDerecha) {
                            enemigo.x = enemigo.hitbox.x = Mundo.ANCHO - anchoEnemigo; // Sale por la derecha
                            enemigo.setEstado(Entidad.Estado.ATRAS);
                        }else{
                            enemigo.x = enemigo.hitbox.x = 0; // Sale por la izquierda
                            enemigo.setEstado(Entidad.Estado.ADELANTE); // Se mueve hacia la derecha
                        }
                        enemigo.y = enemigo.hitbox.y = Mundo.yJuego + rnd.nextFloat() * (Mundo.ALTO - Mundo.yJuego - altoEnemigo);
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
        Enemigo nuevoEnemigo;
        boolean salePorDerecha = rnd.nextBoolean();
        float posY = Mundo.yJuego + rnd.nextFloat() * (Mundo.ALTO - Mundo.yJuego - altoEnemigo);
        if (salePorDerecha) {
            x = Mundo.ANCHO - anchoEnemigo; // Sale por la derecha
            nuevoEnemigo = new Enemigo(x, posY, anchoEnemigo, altoEnemigo, rnd.nextFloat() * 100 + 50);
            nuevoEnemigo.setEstado(Entidad.Estado.ATRAS); // Se mueve hacia la izquierda
        } else {
            x = 0; // Sale por la izquierda
            nuevoEnemigo = new Enemigo(x, posY, anchoEnemigo, altoEnemigo, rnd.nextFloat() * 100 + 50);
            nuevoEnemigo.setEstado(Entidad.Estado.ADELANTE); // Se mueve hacia la derecha
        }
        enemigos.add(nuevoEnemigo);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP ) {
            jugador.moverArriba();
        }
        if (keycode == Input.Keys.DOWN) {
            jugador.moverAbajo();
        }
        if(keycode == Input.Keys.P){
            game.cargarPantallaPausa();
            pause = true;
        }
        if(keycode == Input.Keys.SPACE){
            jugador.cambiarForma();
        }
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
        jugador.reset();

    }
}
