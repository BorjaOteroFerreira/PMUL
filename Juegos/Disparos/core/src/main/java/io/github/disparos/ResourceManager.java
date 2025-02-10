package io.github.disparos;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class ResourceManager {
    private static final AssetManager assetManager = new AssetManager();
    private static final int numSpritesDisparo = 18;
    private static final int numSpritesEnemigoMuerte = 13;
    public static Texture pistola;
    public static Animation<TextureRegion> disparo, enemigoMuerte;

    private ResourceManager() {
    }

    public static void cargarRecursos() {
        assetManager.load("pistola.png", Texture.class);
    }

    public static boolean assetsCargados() {
        return assetManager.update();
    }

    public static float progresoCarga() {
        return assetManager.getProgress();
    }

    public static void asignarRecursos() {
        pistola = assetManager.get("pistola.png", Texture.class);
    }

    public static void liberarRecursos() {
        assetManager.dispose();
    }
}
