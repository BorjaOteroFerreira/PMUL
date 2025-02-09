package io.github.Pescador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture fondo;
    public static BitmapFont fuente;
    public static TextureAtlas atlas;
    public static TextureRegion pescador,anzuelo,sedal, pez;
    public static Animation aniPezAmarillo, aniPezLila;
    public static Sound captura, finDelJuego;
    public static Music musicaDeFondo;
    public static void cargarRecursos(){
        fondo = new Texture(Gdx.files.internal("graficos/fondo.jpg"));
        captura=Gdx.audio.newSound(Gdx.files.internal("sonidos/captura.mp3"));
        finDelJuego=Gdx.audio.newSound(Gdx.files.internal("sonidos/finDelJuego.mp3"));
        musicaDeFondo=Gdx.audio.newMusic(Gdx.files.internal("sonidos/fondo.mp3"));
        //fuente = new BitmapFont(Gdx.files.internal("fuentes/fuente.fnt"));
        fuente = new BitmapFont();
        fuente.getData().setScale(0.7f);
        fuente.setColor(Color.BLACK);
        atlas = new TextureAtlas(Gdx.files.internal("graficos/atlas.atlas"));
        pescador = atlas.findRegion("pescador");
        anzuelo = atlas.findRegion("anzuelo");
        sedal = atlas.findRegion("punto");
        pez = atlas.findRegion("pez");
        TextureRegion pezLila = atlas.findRegion("pez_lila");
        aniPezLila = new Animation(0.25f,pezLila.split(96, 96)[0]);
        TextureRegion pezAmarillo = atlas.findRegion("pez_amarillo");
        TextureRegion[][] framesPezAmarillo=pezAmarillo.split(288/3,192/2);
        int filasPezAmarillo=framesPezAmarillo.length;
        int columnasPezAmarillo=framesPezAmarillo[0].length;
        TextureRegion[] framesPA=new TextureRegion[filasPezAmarillo*columnasPezAmarillo];
        int numFrame=0;
        for(int fila=0;fila<filasPezAmarillo;fila++)
            for(int columna=0;columna<columnasPezAmarillo;columna++)
                framesPA[numFrame++]=framesPezAmarillo[fila][columna];
        aniPezAmarillo=new Animation(.25f,framesPA);
    }
    public static void liberarRecursos(){
        captura.dispose();
        musicaDeFondo.dispose();
        finDelJuego.dispose();
        fondo.dispose();
        fuente.dispose();
        atlas.dispose();
    }
}
