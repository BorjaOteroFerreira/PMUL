package io.github.disparos.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.disparos.ResourceManager;
import io.github.disparos.entidades.Pistola;
import io.github.disparos.mundo.Mundo;
import io.github.disparos.MainGame;


public class PantallaInicio extends Pantalla {

    SpriteBatch sb ;

    public PantallaInicio(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
    }

    @Override
    public void show() {
        ResourceManager.cargarRecursos();
        Pistola pistola = new Pistola();
    }

    @Override
    public void render(float delta) {
        if(ResourceManager.assetsCargados()) {
            ResourceManager.asignarRecursos();
            ScreenUtils.clear(1,1,1,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            sb.begin();
            sb.draw(new Texture("valla.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            ResourceManager.fuente.draw(sb, "HOLA AMIGO PATRIOTA!", Mundo.ANCHO - 100, Mundo.ALTO );
            sb.end();
            if (Gdx.input.isTouched()) {
                SiguientePantalla();
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    public void SiguientePantalla() {
        game.setScreen(new PantallaJuego(game));
    }


    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
