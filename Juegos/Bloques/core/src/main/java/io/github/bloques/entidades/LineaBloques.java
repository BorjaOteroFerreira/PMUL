package io.github.bloques.entidades;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import io.github.bloques.mundo.Mundo;

public class LineaBloques extends Entidad {
    private Array<Bloque> bloques;
    private Random rnd;

    private float anchoBloque = Mundo.ANCHO / Mundo.NUM_BLOQUES_POR_LINEA;
    private float altoBloque = Mundo.ALTO / Mundo.NUM_LINEAS_POR_PANTALLA;
    private static final Color[] COLORES = new Color[]{
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW,
        Color.PURPLE
    };

    public LineaBloques(float x, float y, float ancho, float alto) {
        super(x, y + (Mundo.ALTO / Mundo.NUM_LINEAS_POR_PANTALLA), ancho, alto);
        bloques = new Array<>();
        rnd = new Random();
        crearLineaBloques();
    }

    private void crearLineaBloques() {
        for (int i = 0; i < Mundo.NUM_BLOQUES_POR_LINEA; i++) {
            Color colorAleatorio = COLORES[rnd.nextInt(COLORES.length)];
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
