package io.github.bloques.entidades;

import com.badlogic.gdx.utils.Array;

import io.github.bloques.mundo.Mundo;

public class LineaBloques extends Entidad{
    private Array<Bloque> bloques = new Array<>;
    private final int NUM_BLOQUES = 5;
    private final int NUM_FILAS = 10;
    private float anchoBloque = Mundo.ANCHO / NUM_BLOQUES;
    private float altoBloque = Mundo.ALTO / NUM_FILAS;

    public LineaBloques(float x, float y, float ancho, float alto) {
        super(x, y, ancho, alto);
        crearLineaBloques();
    }

    private void crearLineaBloques() {
        for (int i = 0; i < NUM_BLOQUES; i++) {
            bloques.add(new Bloque(x + i * anchoBloque, y, anchoBloque, altoBloque));
        }
    }
}

