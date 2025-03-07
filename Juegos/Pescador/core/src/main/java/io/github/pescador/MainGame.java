package io.github.pescador;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.pescador .pantallas.Pantalla;
import io.github.pescador.pantallas.PantallaJuego;


public class MainGame extends Game {
    public SpriteBatch batch; // Compartido entre pantallas
    public ShapeRenderer shapeRenderer;
    @Override
    public void create () {
        ResourceManager.cargarRecursos();
        while (!ResourceManager.assetsCargados()) {
            Gdx.app.log("Cargando", "" + ResourceManager.progresoCarga());
        }
        System.out.println("Recursos cargados");
        ResourceManager.asignarRecursos();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        if(ResourceManager.assetsCargados())
            setScreen(new PantallaJuego(this));
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    @Override
    public void dispose () {
        getScreen().dispose();
        batch.dispose();
    }

    public void setPantalla(Pantalla pantalla) {
        setScreen(pantalla);
    }


    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}
