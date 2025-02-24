package io.github.examen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.examen.pantallas.PantallaInicio;
import io.github.examen.pantallas.PantallaJuego;
import io.github.examen.pantallas.PantallaPausa;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private SpriteBatch sb;
    private ShapeRenderer sr ;
    private Texture image;
    private PantallaInicio pantallaInicio;
    private PantallaJuego pantallaJuego;
    private PantallaPausa pantallaPausa;


    @Override
    public void create() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        setScreen(new PantallaInicio(this));
        ResourceManager.cargarRecursos();
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

    public void cargarPantallaPausa(){
        if(pantallaPausa == null){
            pantallaPausa = new PantallaPausa(this);

        }
        setScreen(pantallaPausa);
    }


    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public ShapeRenderer getShapeRenderer(){
        return sr;
    }


    @Override
    public void dispose() {
        sb.dispose();
        sr.dispose();
    }
}
