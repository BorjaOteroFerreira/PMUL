package io.github.bloques.entidades;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import io.github.bloques.ResourceManager;
import io.github.bloques.mundo.Mundo;

public class LineaBloques extends Entidad {
    private Array<Bloque> bloques;
    private Random rnd;

    private float anchoBloque = Mundo.ANCHO / Mundo.NUM_BLOQUES_POR_LINEA;
    private float altoBloque = Mundo.ALTO / Mundo.NUM_LINEAS_POR_PANTALLA;
    private static final Texture[] GELATINAS = new Texture[]{
        ResourceManager.gelatina1,
        ResourceManager.gelatina2,
        ResourceManager.gelatina3,
        ResourceManager.gelatina4,
        ResourceManager.gelatina5,
        ResourceManager.gelatina6,

    };

    public LineaBloques(float x, float y, float ancho, float alto) {
        super(x, y + (Mundo.ALTO / Mundo.NUM_LINEAS_POR_PANTALLA), ancho, alto);
        bloques = new Array<>();
        rnd = new Random();
        crearLineaBloques();
    }

    private void crearLineaBloques() {
        for (int i = 0; i < Mundo.NUM_BLOQUES_POR_LINEA; i++) {
            Texture colorAleatorio = GELATINAS[rnd.nextInt(GELATINAS.length)];
            bloques.add(new Bloque(
                x + i * anchoBloque,
                y + altoBloque,
                anchoBloque,
                altoBloque,
                rnd.nextInt(1, (int) Mundo.NUM_BLOQUES_POR_LINEA),
                colorAleatorio
            ));
        }
    }

    public Array<Bloque> getBloques() {
        return bloques;
    }

    public void update() {
        for (int i = 0; i < bloques.size; i++) {
            bloques.get(i).update();
        }
    }

    public void render(SpriteBatch sb, ShapeRenderer sr) {
        for (int i = 0; i < bloques.size; i++) {
            bloques.get(i).render(sb, sr);
        }

    }
}
