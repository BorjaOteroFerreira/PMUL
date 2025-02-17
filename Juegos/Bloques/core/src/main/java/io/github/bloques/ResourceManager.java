package io.github.bloques;
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
    public static Texture pistola;
    public static Texture bala;
    public static Texture enemigo;
    public static Sound disparoSound;
    public static Sound muerteSound;
    public static Sound segarro2Sound, segarroSound;
    public static Sound endSound;
    public static BitmapFont fuente;
    public static Texture bgIntro ,background2;

    public static Animation<TextureRegion> disparo, enemigoMuerte;

    private ResourceManager() {}

    public static void cargarRecursos() {
        //assetManager.load("pistola.png", Texture.class);
        assetManager.load("bgIntro.png", Texture.class);

    }

    public static boolean assetsCargados() {
        return assetManager.update();
    }
    public static float progresoCarga() {
        return assetManager.getProgress();
    }

    public static void asignarRecursos() {
        fuente = new BitmapFont();
        fuente.setColor(Color.BLACK);
        bgIntro = assetManager.get("bgIntro.png", Texture.class);
        //background = assetManager.get("valla.png", Texture.class);

    }

    public static void liberarRecursos() {
        assetManager.dispose();
    }
}
