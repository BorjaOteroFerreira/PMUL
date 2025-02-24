package io.github.examen;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class ResourceManager  {
    private static final AssetManager assetManager =  new AssetManager();
    public static BitmapFont fuente;
    public static Array<Texture> insectos = new Array<>();
    public static Texture mancha ;


    public static void cargarRecursos(){
        fuente = new BitmapFont();
        fuente.setColor(Color.BLACK);
    }

    public static void asignarRecursos(){

    }

    public static float getProgress(){
        return assetManager.getProgress();
    }

    public static int getInsectosSize(){
        return insectos.size;
    }

    public static boolean recursosCargados(){
        return assetManager.update();
    }

    public static Texture getInsecto(int index){
        return insectos.get(index);
    }

}
