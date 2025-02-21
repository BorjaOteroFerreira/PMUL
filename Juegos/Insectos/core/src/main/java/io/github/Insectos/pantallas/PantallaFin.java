package io.github.Insectos.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.Insectos.MainGame;
import io.github.Insectos.mundo.Mundo;

public class PantallaFin extends Pantalla  {
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

    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl20.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        sb.end();
        sr.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ENTER){
            game.cargarPantallaInicio();
            Mundo.setInsectos(0);
            return true;
        }

        return false;
    }

}
