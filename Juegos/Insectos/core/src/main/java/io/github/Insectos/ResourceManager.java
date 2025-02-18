package io.github.Insectos;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ResourceManager  {
    private static final AssetManager assetManager =  new AssetManager();
    public static BitmapFont fuente;


    public static void asignarRecursos(){
        fuente = new BitmapFont();
        fuente.setColor(Color.BLACK);

    }

}
