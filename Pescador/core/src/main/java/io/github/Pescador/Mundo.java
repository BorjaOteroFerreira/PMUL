package io.github.Pescador;

import java.util.Random;

import io.github.Pescador.Personajes.Anzuelo;
import io.github.Pescador.Personajes.Pescador;
import io.github.Pescador.Personajes.Pez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;

public class Mundo {
    private static Random random = new Random();
    public static int ANCHO = 332;
    public static int ALTO = 200;
    public static int ALTO_LINEA_MAR = 128;
    private static float TiempoInicialDeJuego = 10;
    public static float TiempoBonificacionPesca1Pez = 6;
    public static float TiempoBonificacionPescaMultiPez = 3;
    private static float TIEMPO_MIN_ENTRE_PECES = 0.3f;
    public static boolean ANZUELO_MULTI_PEZ = true;
    public static float TiempoBonificacionPesca = ANZUELO_MULTI_PEZ
        ? TiempoBonificacionPescaMultiPez
        : TiempoBonificacionPesca1Pez;
    public static boolean MODO_DEBUG = false;
    public static Pescador pescador = new Pescador();
    public static Anzuelo anzuelo = new Anzuelo();
    public static Array<Pez> peces = new Array();
    public static Array<Pez> pescados = new Array();
    public static float TiempoTotalDeJuego, stateTime, stateTimeProximoPez;
    private static int numCapturas;

    public static void reset() {
        pescador.reset();
        anzuelo.reset();
        peces.clear();
        numCapturas = 0;
        TiempoTotalDeJuego = TiempoInicialDeJuego;
        stateTime = 0;
        stateTimeProximoPez = getRandomProximoPez();
        Assets.musicaDeFondo.play();
    }

    public static void fin() {
        Assets.musicaDeFondo.stop();
        Assets.finDelJuego.play();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        //reset();
        Gdx.app.exit();
    }

    public static String getInfo() {
        StringBuilder str = new StringBuilder((int) (TiempoTotalDeJuego - stateTime) + "s. ");
        str.append("Pescados:").append(numCapturas);
        if (Mundo.ANZUELO_MULTI_PEZ) {
            int pecesEnAnzuelo = Mundo.anzuelo.getPescados();
            if (pecesEnAnzuelo > 1) str.append(" +").append(pecesEnAnzuelo);
        }
        if (MODO_DEBUG) str.append(getInfoDebug());
        return str.toString();
    }

    public static String getInfoDebug() {
        return " Peces en el agua:" + Mundo.peces.size;
    }

    //region Gestión Peces
    public static void creaPez() {
        peces.add(Pez.creaNuevoPez());
        stateTimeProximoPez = stateTime + getRandomProximoPez();
    }

    public static void eliminaPez(Pez pez) {
        peces.removeValue(pez, true);
    }

    public static void dibujaPeces(SpriteBatch sb, ShapeRenderer sr) {
        for (Pez pez : peces) pez.dibuja(sb, sr);
    }

    public static void actualizaPeces(float delta) {
        for (Pez pez : peces) {
            pez.actualiza(delta);
            if (anzuelo.enModoPesca() && !pez.estaPescado()
                && (ANZUELO_MULTI_PEZ ||
                anzuelo.vacio()))
                if (Intersector.overlaps(Mundo.anzuelo.getHitBox(), pez.getHitBox()))
                    if (anzuelo.vacio())
                        Mundo.anzuelo.pescado(pez);
                    else if (ANZUELO_MULTI_PEZ && !pez.estaPescado())
                        Mundo.anzuelo.pescado(pez);
        }
    }

    public static void pezALaCesta(Pez pescado) {
        TiempoTotalDeJuego += TiempoBonificacionPesca;
        numCapturas++;
        Assets.captura.play();
        pescados.add(pescado);
        eliminaPez(pescado);
    }

    public static void pezFueraDelMundo(Pez pez) {
        eliminaPez(pez);
    }

    public static float getRandomProximoPez() {
        float TIEMPO_MAX_ENTRE_PECES = 0.6f;
        return TIEMPO_MIN_ENTRE_PECES
            + random.nextFloat() * (TIEMPO_MAX_ENTRE_PECES - TIEMPO_MIN_ENTRE_PECES);
    }
}
