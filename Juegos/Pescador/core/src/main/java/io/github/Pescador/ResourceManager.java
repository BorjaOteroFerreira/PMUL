package io.github.pescador;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class ResourceManager {
    private static final AssetManager assetManager = new AssetManager();

    public static BitmapFont fuente;
    public static Texture background, background2;
    public static TextureRegion pescador, anzuelo , pez, enemigo;
    public static TextureRegion pezLila;
    public static Animation<TextureRegion> animacionLila ;


    private ResourceManager() {}

    public static void cargarRecursos() {
        assetManager.load("graficos/fondo.jpg", Texture.class);
        assetManager.load("graficos/atlas.atlas", TextureAtlas.class);

    }

    public static boolean assetsCargados() {
        return assetManager.update();
    }

    public static float progresoCarga() {
        return assetManager.getProgress();
    }

    public static void asignarRecursos() {
        TextureAtlas atlas = assetManager.get("graficos/atlas.atlas", TextureAtlas.class);
        pescador = atlas.findRegion("pescador");
        anzuelo = atlas.findRegion("anzuelo");
        pezLila = atlas.findRegion("pez_lila");
        TextureRegion[][] animacion = pezLila.split(96, 96);
        animacionLila = new Animation<>(0.1f, animacion[0]);

        fuente = new BitmapFont();
        fuente.setColor(Color.BLACK);
        background = assetManager.get("graficos/fondo.jpg", Texture.class);
    }

    public static void liberarRecursos() {
        assetManager.dispose();
    }
}
