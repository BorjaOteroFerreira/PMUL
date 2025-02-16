package io.github.disparos.pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.disparos.ResourceManager;
import io.github.disparos.entidades.Pistola;
import io.github.disparos.mundo.Mundo;
import io.github.disparos.MainGame;


public class PantallaInicio extends Pantalla {

    SpriteBatch sb ;
    private boolean recursosAsignados = false;

    public PantallaInicio(MainGame game) {
        super(game);
        sb = game.getSpriteBatch();
    }

    @Override
    public void show() {
        super.show();
        ResourceManager.cargarRecursos();
        Pistola pistola = new Pistola();
    }

    @Override
    public void render(float delta) {
        if(ResourceManager.assetsCargados()) {
            if (!recursosAsignados) {
                ResourceManager.asignarRecursos();
                recursosAsignados = true;
            }
            ScreenUtils.clear(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            sb.begin();
            sb.draw(ResourceManager.background, 0, 0, Mundo.ANCHO, Mundo.ALTO);
            ResourceManager.fuente.draw(sb, "HOLA  AMIGO PATRIOTA!", Mundo.ANCHO / 2 - 80, Mundo.ALTO / 2);
            ResourceManager.fuente.draw(sb, "Proteje la frontera de los pelo-brocoli!", Mundo.ANCHO / 2 - 120, Mundo.ALTO / 2 - 15);
            sb.end();
            if (Gdx.input.isTouched()) {
                SiguientePantalla();
            }
        }
    }

    public void SiguientePantalla() {
        game.setScreen(new PantallaJuego(game));
    }

}
