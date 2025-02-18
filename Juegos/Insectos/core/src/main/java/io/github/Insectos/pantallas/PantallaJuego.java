package io.github.Insectos.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.Insectos.MainGame;
import io.github.Insectos.ResourceManager;
import io.github.Insectos.mundo.Mundo;

public class PantallaJuego extends Pantalla{
    SpriteBatch sb;

    public PantallaJuego(MainGame game) {
        super(game);
    }

    public void show() {
        super.show();
    }

    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb =  game.getSpriteBatch();
        sb.begin();
        ResourceManager.fuente.draw(sb, "Juego", Mundo.ANCHO / 2, Mundo.ALTO / 2);
        sb.end();
    }
}
