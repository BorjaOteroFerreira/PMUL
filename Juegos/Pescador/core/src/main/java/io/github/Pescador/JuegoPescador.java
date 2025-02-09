package io.github.Pescador;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class JuegoPescador extends ApplicationAdapter {
    OrthographicCamera camara=new OrthographicCamera();
    SpriteBatch sb;
    ShapeRenderer sr;
    @Override
    public void create() {
        sb=new SpriteBatch();
        sr=new ShapeRenderer();
        Gdx.input.setInputProcessor(new ProcesadorDeEntrada());
        Assets.cargarRecursos();
        Mundo.reset();
    }
    @Override
    public void resize(int width, int height) {
        camara.setToOrtho(false,Mundo.ANCHO,Mundo.ALTO);
        camara.update();
        sb.setProjectionMatrix(camara.combined);
        sr.setProjectionMatrix(camara.combined);
    }
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta=Gdx.graphics.getDeltaTime();
        Mundo.stateTime += delta;
        if((Mundo.TiempoTotalDeJuego-Mundo.stateTime)<0) Mundo.fin();
        if(Mundo.stateTime>Mundo.stateTimeProximoPez)
            Mundo.creaPez();
        Mundo.pescador.actualiza(delta);
        Mundo.anzuelo.actualiza(delta);
        Mundo.actualizaPeces(delta);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        sb.draw(Assets.fondo,0,0,Mundo.ANCHO,Mundo.ALTO);
        Assets.fuente.draw(sb,Mundo.getInfo(),10,Mundo.ALTO-10);
        Mundo.pescador.dibuja(sb,sr);
        Mundo.anzuelo.dibuja(sb,sr);
        Mundo.dibujaPeces(sb,sr);
        sb.end();
        sr.end();
    }
    @Override
    public void dispose() {
        Assets.liberarRecursos();
    }
}
