package io.github.Insectos.pantallas;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.Insectos.MainGame;
import io.github.Insectos.ResourceManager;
import io.github.Insectos.mundo.Mundo;

public class PantallaFin extends Pantalla{

    SpriteBatch sb;
    ShapeRenderer sr;

    public PantallaFin(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta){
        super.render(delta);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        ResourceManager.fuente.draw(sb, "Fin del juego", Mundo.ANCHO / 2 , Mundo.ALTO / 2);
        sb.end();
        sr.end();
    }
}
