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
    public static Texture pistola;
    public static Texture bala;
    public static Texture enemigo;
    public static Sound disparoSound;
    public static Sound muerteSound;
    public static Sound segarro2Sound, segarroSound;
    public static Sound endSound;
    public static BitmapFont fuente;
    public static Texture background ,background2;

    public static Animation<TextureRegion> disparo, enemigoMuerte;

    private ResourceManager() {}

    public static void cargarRecursos() {
        assetManager.load("pistola.png", Texture.class);
        assetManager.load("bala.png", Texture.class);
        assetManager.load("puñalin.png", Texture.class);
        assetManager.load("pistola.mp3", Sound.class);
        assetManager.load("muerte.mp3", Sound.class);
        assetManager.load("valla.png", Texture.class);
        assetManager.load("segarro.mp3", Sound.class);
        assetManager.load("segarro2.mp3", Sound.class);
        assetManager.load("endbg.jpg", Texture.class);
        assetManager.load("caralsolSound.mp3", Sound.class);
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
        background = assetManager.get("valla.png", Texture.class);
        background2 = assetManager.get("endbg.jpg", Texture.class);
        pistola = assetManager.get("pistola.png", Texture.class);
        bala = assetManager.get("bala.png", Texture.class);
        enemigo = assetManager.get("puñalin.png", Texture.class);
        disparoSound = assetManager.get("pistola.mp3", Sound.class);
        muerteSound = assetManager.get("muerte.mp3", Sound.class);
        segarroSound = assetManager.get("segarro.mp3", Sound.class);
        segarro2Sound = assetManager.get("segarro2.mp3", Sound.class);
        endSound = assetManager.get("caralsolSound.mp3", Sound.class);
    }

    public static void liberarRecursos() {
        assetManager.dispose();
    }
}
