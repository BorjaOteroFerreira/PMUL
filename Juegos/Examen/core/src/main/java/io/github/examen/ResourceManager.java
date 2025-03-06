package io.github.examen;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class ResourceManager  {
    private static final AssetManager assetManager =  new AssetManager();
    public static BitmapFont fuente;

    public static void cargarRecursos(){
        fuente = new BitmapFont();
        fuente.setColor(Color.BLACK);
    }

    public static float getProgress(){
        return assetManager.getProgress();
    }


    public static boolean recursosCargados(){
        return assetManager.update();
    }

}
