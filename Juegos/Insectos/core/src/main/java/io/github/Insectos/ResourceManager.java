package io.github.Insectos;

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
        assetManager.load("hormiga.png", Texture.class);
        assetManager.load("escarabajo.png", Texture.class);
        assetManager.load("mariquita.png", Texture.class);
        assetManager.load("abeja.png", Texture.class);
        assetManager.load("mosca.png", Texture.class);
        assetManager.load("arana.png", Texture.class);
        assetManager.load("cucaracha.png", Texture.class);
        assetManager.load("avispa.png", Texture.class);
        assetManager.load("bicho.png", Texture.class);
        assetManager.load("mancha.png", Texture.class);
    }

    public static void asignarRecursos(){
        insectos.add(assetManager.get("hormiga.png"));
        insectos.add(assetManager.get("escarabajo.png"));
        insectos.add(assetManager.get("mariquita.png"));
        insectos.add(assetManager.get("abeja.png"));
        insectos.add(assetManager.get("mosca.png"));
        insectos.add(assetManager.get("arana.png"));
        insectos.add(assetManager.get("cucaracha.png"));
        insectos.add(assetManager.get("avispa.png"));
        insectos.add(assetManager.get("bicho.png"));
        mancha =  assetManager.get("mancha.png");
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
