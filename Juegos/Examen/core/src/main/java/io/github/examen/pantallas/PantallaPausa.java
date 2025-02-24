package io.github.examen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.examen.MainGame;
import io.github.examen.ResourceManager;
import io.github.examen.mundo.Mundo;

public class PantallaPausa extends Pantalla{
    SpriteBatch sb;
    public PantallaPausa(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        ResourceManager.fuente.draw(sb, "PAUSA" , Mundo.ANCHO / 2 , Mundo.ALTO / 2);
        sb.end();
    }

    @Override
    public boolean keyDown(int keyCode){
        game.cargarPantallaJuego();
        return true;
    }

}
