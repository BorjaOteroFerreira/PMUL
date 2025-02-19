package io.github.bloques;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.bloques.pantallas.Pantalla;
import io.github.bloques.pantallas.PantallaInicio;
import io.github.bloques.pantallas.PantallaJuego;
import io.github.bloques.pantallas.PantallaFin;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private PantallaInicio pantallaInicio;
    private PantallaJuego pantallaJuego;
    private PantallaFin pantallaFin;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        ResourceManager.cargarRecursos();
        mostrarPantallaInicio();
    }

    public void mostrarPantallaInicio() {
        if(pantallaInicio == null){
            pantallaInicio = new PantallaInicio(this);
        }
        pantallaInicio.reset();
        setScreen(pantallaInicio);
    }

    public void mostrarPantallaJuego() {
        if(pantallaJuego == null){
            pantallaJuego = new PantallaJuego(this);
        }
        pantallaJuego.reset();
        setScreen(pantallaJuego);
    }

    public void mostrarPantallaFin() {
        if(pantallaFin == null){
            pantallaFin = new PantallaFin(this);
        }
        pantallaFin.reset();
        setScreen(pantallaFin);
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        pantallaInicio.dispose();
        pantallaJuego.dispose();
        pantallaFin.dispose();
    }
}
