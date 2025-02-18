package io.github.Insectos.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.Insectos.MainGame;
import io.github.Insectos.ResourceManager;
import io.github.Insectos.mundo.Mundo;

public class PantallaInicio extends Pantalla{
    SpriteBatch sb;

    public PantallaInicio(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
    }

    @Override
    public void show(){
        super.show();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();

        ResourceManager.fuente.draw(sb,"INICIO", Mundo.ANCHO / 2 , Mundo.ALTO / 2);
        sb.end();
        super.render(delta);
    }
}
