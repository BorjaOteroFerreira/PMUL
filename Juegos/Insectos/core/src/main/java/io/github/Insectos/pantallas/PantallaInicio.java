package io.github.Insectos.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.Insectos.MainGame;
import io.github.Insectos.ResourceManager;
import io.github.Insectos.entidades.Insecto;
import io.github.Insectos.mundo.Mundo;

public class PantallaInicio extends Pantalla{
    SpriteBatch sb;
    private ShapeRenderer sr;
    private float segundosEntreInsectos = 1f;
    private float proximoInsecto = 0f;
    private float stateTime = 0f;
    private Insecto insecto ;
    private int numInsectos = 0;
    private boolean recursosAsignados = false;
    private float anchoImagen = 100f;
    private float altoImagen = 100f;
    public PantallaInicio(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
    }

    @Override
    public void show(){
        super.show();
        stateTime = 0;
        numInsectos = 0;
        proximoInsecto = 0;
        insecto = new Insecto(Mundo.ANCHO / 2 - anchoImagen / 2, Mundo.ALTO / 2 - altoImagen / 2, anchoImagen, altoImagen, 10);
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        ScreenUtils.clear(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (stateTime > proximoInsecto) {
            insecto.setImagen(ResourceManager.getInsecto(numInsectos));
            numInsectos++;
            if (numInsectos >= ResourceManager.getInsectosSize()) {
                numInsectos = 0;
            }
            proximoInsecto = stateTime + segundosEntreInsectos;
        }
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        insecto.render(sb,sr);
        int fnumInssectos = numInsectos == 0  ? 9 : numInsectos;
        ResourceManager.fuente.draw(sb, "Insectos: " + (fnumInssectos ) + "/" + ResourceManager.getInsectosSize(),
            Mundo.ANCHO / 2, Mundo.ALTO / 2);
        sb.end();
        sr.end();
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
            case Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.NUM_4, Input.Keys.NUM_5, Input.Keys.NUM_6, Input.Keys.NUM_7, Input.Keys.NUM_8, Input.Keys.NUM_9:
                Mundo.setInsectos(keycode - Input.Keys.NUM_1 + 1);
                game.cargarPantallaJuego();
                break;
        }
        if(keycode == Input.Keys.F){
            Gdx.app.exit();
        }
         return false;
    }
}
