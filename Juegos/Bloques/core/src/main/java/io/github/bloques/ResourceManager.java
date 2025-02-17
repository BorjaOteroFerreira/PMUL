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
    public static Texture bgIntro ,background2;

    private ResourceManager() {}

    public static void cargarRecursos() {
        assetManager.load("bgIntro.png", Texture.class);

    }
    public static boolean assetsCargados() {
        return assetManager.update();
    }

    public static void asignarRecursos() {
        fuente = new BitmapFont();
        fuente.setColor(Color.BLACK);
        bgIntro = assetManager.get("bgIntro.png", Texture.class);
    }
    public static void liberarRecursos() {
        assetManager.dispose();
    }
}
