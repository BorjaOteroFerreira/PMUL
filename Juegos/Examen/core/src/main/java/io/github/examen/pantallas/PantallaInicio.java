package io.github.examen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.examen.MainGame;
import io.github.examen.RecordManager;
import io.github.examen.ResourceManager;
import io.github.examen.mundo.Mundo;

public class PantallaInicio extends Pantalla {
    ShapeRenderer sr;
    SpriteBatch sb;
    float record ;
    RecordManager recordManager;

    public PantallaInicio(MainGame game) {
        super(game);
        sb= game.getSpriteBatch();
        sr = game.getShapeRenderer();
        recordManager = RecordManager.getInstance();
    }

    @Override
    public void show(){
        super.show();
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sr.setColor(Color.YELLOW);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(0, Mundo.ALTO / 2, Mundo.ANCHO, Mundo.ALTO / 2);
        sr.setColor(Color.GREEN);
        sr.rect(0, 0, Mundo.ANCHO, Mundo.ALTO /2 );
        sr.end();
        sb.begin();
        ResourceManager.fuente.getData().setScale(2f);
        ResourceManager.fuente.draw(sb, "Jugar (1...9)" , Mundo.ANCHO / 2 - 75 ,(Mundo.ALTO / 2) + Mundo.ALTO / 4 );
        record = recordManager.getRecord("record_"+ Mundo.numColisiones);
        if(recordManager.existeRecord("record_"+ Mundo.numColisiones)) {
            ResourceManager.fuente.draw(sb, "Record :" + record, Mundo.ANCHO / 2 - 150, Mundo.ALTO / 4);
        }
        sb.end();
    }

    @Override
    public void dispose(){
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode){
        switch(keycode){
            case Input.Keys.R:
                break;
            case Input.Keys.F:
                Gdx.app.exit();
                break;
            case Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3,
                 Input.Keys.NUM_4, Input.Keys.NUM_5, Input.Keys.NUM_6,
                 Input.Keys.NUM_7, Input.Keys.NUM_8, Input.Keys.NUM_9:
                Mundo.setNumColisiones(keycode - Input.Keys.NUM_1 + 1);
                game.cargarPantallaJuego();
                break;
        }
        if(keycode == Input.Keys.F){
            Gdx.app.exit();
        }
        return false;
    }


}


