package io.github.disparos.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;
import io.github.disparos.MainGame;
import io.github.disparos.ResourceManager;
import io.github.disparos.mundo.Mundo;

public class PantallaFin extends Pantalla {

    private boolean recursosAsignados = false;

    SpriteBatch sb;
    public PantallaFin(MainGame game) {
        super(game);
    }
    @Override
    public void show() {
        super.show();
        System.out.println("Cargar recursos");
        ResourceManager.endSound.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        sb = game.getSpriteBatch();
        sb.begin();
        sb.draw(ResourceManager.background2, 0, 0, Mundo.ANCHO, Mundo.ALTO);
        ResourceManager.fuente.setColor(1, 1, 1, 1);
        ResourceManager.fuente.draw(game.getSpriteBatch(),
            "Tu pais te agradece tus servicios",
            Mundo.ANCHO - 280,
            Mundo.ALTO - 20);
        sb.end();
    }
}
