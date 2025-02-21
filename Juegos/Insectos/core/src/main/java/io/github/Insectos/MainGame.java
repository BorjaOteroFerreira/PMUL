package io.github.Insectos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import io.github.Insectos.pantallas.PantallaCarga;
import io.github.Insectos.pantallas.PantallaFin;
import io.github.Insectos.pantallas.PantallaInicio;
import io.github.Insectos.pantallas.PantallaJuego;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private PantallaInicio pantallaInicio;
    private PantallaJuego pantallaJuego;
    private PantallaFin pantallaFin;


    @Override
    public void create() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        ResourceManager.cargarRecursos();
        setScreen(new PantallaCarga(this));
    }

    public void cargarPantallaInicio(){
        if(pantallaInicio == null){
            pantallaInicio = new PantallaInicio(this);
        }
        setScreen(pantallaInicio);

    }

    public void cargarPantallaJuego(){
        if(pantallaJuego == null){
            pantallaJuego = new PantallaJuego(this);

        }
        setScreen(pantallaJuego);
    }

    public void cargarPantallaFin(){
        if(pantallaFin == null){
            pantallaFin = new PantallaFin(this);

        }
        setScreen(pantallaFin);
    }

    @Override
    public void dispose() {
        getScreen().dispose();
        sb.dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public ShapeRenderer getShapeRenderer() {
        return sr;
    }

}
