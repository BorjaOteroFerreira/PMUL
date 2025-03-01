package io.github.bloques.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.bloques.MainGame;
import io.github.bloques.ResourceManager;


public class PantallaFin extends Pantalla {
    SpriteBatch sb ;
    ShapeRenderer sr;
    Preferences prefs ;
    String mensaje ;
    public boolean recursosAsignados = false;

    public PantallaFin(MainGame game) {
        super(game);
        prefs = Gdx.app.getPreferences("Records");
        mensaje = "Puntuación: " + PantallaJuego.puntos + "\nRecord anterior: " + prefs.getInteger("record", 0);
        comprobarRecord();
    }

    @Override
    public void show() {
        super.show();
        ResourceManager.fuente.getData().setScale(2.0f);

    }
    @Override
    public void render(float delta) {
        super.render(delta);
        sb = game.getSpriteBatch();
        sr = game.getShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        sb.draw(ResourceManager.bgFinal, 0, 0, ANCHO, ALTO);
        // Aumentar el tamaño de la fuente
        ResourceManager.fuente.draw(sb, mensaje, (ANCHO / 2) - 90, (ALTO / 2) + 50);
        sb.end();
        sr.end();
    }

    public void comprobarRecord(){
        if (PantallaJuego.puntos > prefs.getInteger("record", 0)) {
            prefs.putInteger("record", PantallaJuego.puntos);
            prefs.flush();
            mensaje = "¡Nuevo record!\n" + mensaje;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        PantallaJuego.puntos = 0;
        ResourceManager.bso.stop();
        game.mostrarPantallaInicio();
        return true;
    }

    public void reset(){
        mensaje = "Puntuación: " + PantallaJuego.puntos + "\nRecord anterior: " + prefs.getInteger("record", 0);
        comprobarRecord();
    }
}
