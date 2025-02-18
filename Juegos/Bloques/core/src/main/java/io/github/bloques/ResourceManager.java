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
    public static BitmapFont fuente;
    public static Texture bgIntro ,bgJuego, bgFinal;
    public static Sound bso ;
    public static Texture gelatina1, gelatina2, gelatina3, gelatina4, gelatina5, gelatina6;

    private ResourceManager() {}

    public static void cargarRecursos() {

        assetManager.load("gelatinas/gelatina1.png", Texture.class);
        assetManager.load("gelatinas/gelatina2.png", Texture.class);
        assetManager.load("gelatinas/gelatina3.png", Texture.class);
        assetManager.load("gelatinas/gelatina4.png", Texture.class);
        assetManager.load("gelatinas/gelatina5.png", Texture.class);
        assetManager.load("gelatinas/gelatina6.png", Texture.class);
        assetManager.load("bso.mp3", Sound.class);
        assetManager.load("bgs/bgIntro.png", Texture.class);
        assetManager.load("bgs/bgFinal.png", Texture.class);


    }
    public static boolean assetsCargados() {
        return assetManager.update();
    }

    public static void asignarRecursos() {
        fuente = new BitmapFont();
        fuente.setColor(Color.BLACK);
        bgIntro = assetManager.get("bgs/bgIntro.png", Texture.class);
        bgFinal = assetManager.get("bgs/bgFinal.png", Texture.class);
        bso = assetManager.get("bso.mp3", Sound.class);

        gelatina1 = assetManager.get("gelatinas/gelatina1.png", Texture.class);
        gelatina2 = assetManager.get("gelatinas/gelatina2.png", Texture.class);
        gelatina3 = assetManager.get("gelatinas/gelatina3.png", Texture.class);
        gelatina4 = assetManager.get("gelatinas/gelatina4.png", Texture.class);
        gelatina5 = assetManager.get("gelatinas/gelatina5.png", Texture.class);
        gelatina6 = assetManager.get("gelatinas/gelatina6.png", Texture.class);

    }
    public static void liberarRecursos() {
        assetManager.dispose();
    }
}
