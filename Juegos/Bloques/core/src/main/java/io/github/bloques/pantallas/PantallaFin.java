package io.github.bloques.pantallas;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.bloques.MainGame;
import io.github.bloques.ResourceManager;


public class PantallaFin extends Pantalla {
    SpriteBatch sb ;
    ShapeRenderer sr;

    public PantallaFin(MainGame game) {
        super(game);
    }


    @Override
    public void render(float delta){
        super.render(delta);
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        sb.draw(ResourceManager.bgIntro, 0, 0, ANCHO, ALTO);
        ResourceManager.fuente.getData().setScale(2.0f); // Aumentar el tamaño de la fuente
        ResourceManager.fuente.draw(sb, "Fin del juego", (ANCHO /2) -80, (ALTO /2) -50 );
        sb.end();
        sr.end();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.setScreen(new PantallaInicio(game));
        return true;
    }


}
