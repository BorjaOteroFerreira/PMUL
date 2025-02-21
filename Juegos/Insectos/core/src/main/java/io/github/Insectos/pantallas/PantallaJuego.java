package io.github.Insectos.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.Insectos.MainGame;
import io.github.Insectos.ResourceManager;
import io.github.Insectos.entidades.Insecto;
import io.github.Insectos.mundo.Mundo;

public class PantallaJuego extends Pantalla{
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private int numInsectos = Mundo.numInsectos;
    private Insecto insecto ;
    private final float anchoInsecto = 100 ;
    private final float altoInsecto = 100;
    private int  insectoActual = 0;
    private Array<Vector2> manchas = new Array<>();
    boolean tengoManchas;

    public PantallaJuego(MainGame game) {
        super(game);
        sr = game.getShapeRenderer();
        sb =  game.getSpriteBatch();
        insecto = new Insecto(Mundo.ANCHO / 2 - anchoInsecto / 2, Mundo.ALTO / 2 - altoInsecto / 2, anchoInsecto, altoInsecto, 10);
    }

    public void show() {
        super.show();
        numInsectos = Mundo.numInsectos;
        insectoActual = 0;
        insecto.setImagen(ResourceManager.getInsecto(insectoActual));
        tengoManchas = !manchas.isEmpty();

    }

    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        insecto.update(delta);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        if(tengoManchas){
            for(Vector2 mancha : manchas){
                sr.setColor(Color.BLACK);
                sb.draw(ResourceManager.mancha, mancha.x, mancha.y, anchoInsecto, altoInsecto);
            }
        }
        insecto.render(sb, sr);

        sb.end();
        sr.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        if (insecto.hitbox.contains(touchPos.x, touchPos.y)) {
            insectoActual++;
            if (insectoActual >= numInsectos) {
                manchas.add(new Vector2(touchPos.x, touchPos.y));
                game.cargarPantallaFin();
            }
            insecto.setImagen(ResourceManager.getInsecto(insectoActual));
        }
        return false;
    }


    public void dispose() {
        super.dispose();
    }
}
