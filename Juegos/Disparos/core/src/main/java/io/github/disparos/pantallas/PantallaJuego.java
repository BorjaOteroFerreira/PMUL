package io.github.disparos.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
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
    private int NumEnemigos = 50;
    private static  float tiempoEntreEnemigos = 1.5f;
    private float stateTime = 0;
    private float stateTimeProximoEnemigo = tiempoEntreEnemigos;
    private Array<Enemigo> enemigos = new Array();
    public int enemigosEliminados = 0;
    private  int maxEnemigos = 10;
    private final Random random;
    private boolean crearEnemigo;
    SpriteBatch sb ;
    ShapeRenderer sr;
    private boolean recursosAsignados = false;
    private boolean isPressedTouchUp = false;
    private boolean isPressedTouchDown = false;

    public void renderStats() {
        ResourceManager.fuente.draw(game.getSpriteBatch(), "EArray" + enemigos.size + " Eliminados: " + enemigosEliminados, Mundo.ANCHO - 250, 20);
    }

    public PantallaJuego(MainGame game) {
        super(game);
        random = new Random();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        if(enemigosEliminados == NumEnemigos){
            game.setScreen(new PantallaFin(game));
        }
        ScreenUtils.clear(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen with the specified color
        stateTime += delta;
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
        construirEnemigos();
        for (Enemigo enemigo : enemigos) {
            enemigo.update(delta);
        }
        pistola.update(delta);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        sb.draw(ResourceManager.background, 0, 0, Mundo.ANCHO, Mundo.ALTO);
        renderStats();
        sr.setColor(1, 1, 1, 1);
        pistola.render(sb, sr);
        for (Enemigo enemigo : enemigos) {
            enemigo.render(sb, sr);
        }
        sb.end();
        sr.end();
        tiempoEntreEnemigos -= 0.0001f;
        comprobarColisiones();
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
            int ran = random.nextInt(0, 10);
            if (ran >= 5)
                ResourceManager.segarro2Sound.play();
            else
                ResourceManager.segarroSound.play();
            stateTimeProximoEnemigo = stateTime + tiempoEntreEnemigos;
        }
    }
    private void crearEnemigo() {
        if (enemigos.size <= maxEnemigos) {
            Enemigo nuevoEnemigo = new Enemigo();
            nuevoEnemigo.setEstado(Entidad.Estado.ADELANTE);
            enemigos.add(nuevoEnemigo);
        }
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP ) {
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

    @Override
    public boolean keyUp(int keycode) {
        if ((keycode == Input.Keys.UP || keycode == Input.Keys.DOWN) ) {
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                pistola.moverArriba();
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                pistola.moverAbajo();
            }else
                pistola.setEstado(Entidad.Estado.PARADO);
        }
        return true;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vector = new Vector3(screenX, screenY, 0);
        camara.unproject(vector);
        if (vector.y > Mundo.ALTO / 2 && vector.x < Mundo.ANCHO / 2) {
            pistola.moverArriba();
            isPressedTouchUp = true;
        } else if (vector.y < Mundo.ALTO / 2 && vector.x < Mundo.ANCHO / 2) {
            pistola.moverAbajo();
            isPressedTouchDown = true;
        }
        if (vector.x > Mundo.ANCHO / 2) {
            pistola.disparar();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 vector = new Vector3(screenX, screenY, 0);
        camara.unproject(vector);
        if (vector.y > Mundo.ALTO / 2 && vector.x < Mundo.ANCHO / 2) {
            if (!isPressedTouchDown) {
                pistola.setEstado(Entidad.Estado.PARADO);
            }
            isPressedTouchUp = false;
        } else if (vector.y < Mundo.ALTO / 2 && vector.x < Mundo.ANCHO / 2) {
            if (!isPressedTouchUp) {
                pistola.setEstado(Entidad.Estado.PARADO);
            }
            isPressedTouchDown = false;
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
