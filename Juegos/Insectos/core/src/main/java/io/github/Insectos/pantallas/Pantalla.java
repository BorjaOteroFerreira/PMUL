package io.github.Insectos.pantallas;

import com.badlogic.gdx.graphics.OrthographicCamera;

import io.github.Insectos.MainGame;
import io.github.Insectos.mundo.Mundo;

public class Pantalla {
    public float ANCHO, ALTO;
    private OrthographicCamera camera ;
    private MainGame game;

    public Pantalla(MainGame game) {
        this.game = game;
        ANCHO = Mundo.ANCHO;
        ALTO = Mundo.ALTO;
        camera = new OrthographicCamera(ANCHO, ALTO);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void render(float delta) {
    }
}
