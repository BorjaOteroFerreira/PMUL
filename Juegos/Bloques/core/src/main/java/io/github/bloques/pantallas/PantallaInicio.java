package io.github.bloques.pantallas;
import io.github.bloques.ResourceManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import io.github.bloques.MainGame;

public class PantallaInicio extends Pantalla {
    SpriteBatch sb;
    ShapeRenderer sr;

    public PantallaInicio(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        ResourceManager.cargarRecursos();
    }

    public void render(float delta) {
        super.render(delta);
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        if(ResourceManager.assetsCargados()){
            ResourceManager.asignarRecursos();
            ResourceManager.fuente.getData().setScale(2.0f); // Aumentar el tamaño de la fuente
            sb.draw(ResourceManager.bgIntro, 0, 0, ANCHO, ALTO);
            ResourceManager.fuente.draw(sb, "Toca para empezar", (ANCHO /2) -80, (ALTO /2) -50 );
            // Dibujar texto en formato párrafo
            String parrafo = "\nDestruye los bloques haciendo parejas del mismo tipo.\n"+
                "La velocidad de aparición de los bloques irá aumentando con el tiempo.\n"+
                "Intenta evitar que los bloques lleguen a la parte superior de la pantalla.";
            ResourceManager.fuente.draw(sb, parrafo, 10, ALTO - 150, ANCHO - 20, Align.center, true);
        }
        sb.end();
        sr.end();
    }


    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.setScreen(new PantallaJuego(game));
        return false;
    }

}
