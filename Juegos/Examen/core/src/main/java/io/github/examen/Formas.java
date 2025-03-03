package io.github.examen;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public  class Formas {

    public static void pintarCuadrado(ShapeRenderer sr, float x, float y , float ancho, float alto){
        sr.rect(x, y, ancho, alto);
    }

    public static void pintarCirculo(ShapeRenderer sr, float x, float y , float ancho, float alto){
        //sr.rect(x, y, ancho, alto);
        sr.circle(x + ancho / 2, y + alto / 2, ancho / 2);
    }

    public static void pintarCruz(ShapeRenderer sr, float x, float y , float ancho, float alto){
        //sr.rect(x, y, ancho, alto);
        // Línea horizontal en el centro
        sr.line(x, y + alto / 2, x + ancho, y + alto / 2);
        // Línea vertical en el centro
        sr.line(x + ancho / 2, y, x + ancho / 2, y + alto);
    }
}
