package io.github.examen;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.examen.mundo.Mundo;

public  class Formas {

    public static void pintarCuadrado(ShapeRenderer sr, float x, float y , float ancho, float alto){
        sr.rect(x, y, ancho, alto);
    }

    public static void pintarCirculo(ShapeRenderer sr, float x, float y , float ancho, float alto){
        if (Mundo.debugMode) sr.rect(x, y, ancho, alto);
        sr.circle(x + ancho / 2, y + alto / 2, ancho / 2);
    }

    public static void pintarCruz(ShapeRenderer sr, float x, float y , float ancho, float alto){
        if (Mundo.debugMode) sr.rect(x, y, ancho, alto);
        // Línea horizontal en el centro
        sr.line(x, y + alto / 2, x + ancho, y + alto / 2);
        // Línea vertical en el centro
        sr.line(x + ancho / 2, y, x + ancho / 2, y + alto);
    }

    public static void pintarTriangulo(ShapeRenderer sr, float x, float y , float ancho, float alto){
        if (Mundo.debugMode) sr.rect(x, y, ancho, alto);
        // Línea horizontal en el centro
        sr.triangle(x, y, x + ancho, y, x + ancho / 2, y + alto);
    }

    public static void pintarRombo(ShapeRenderer sr, float x, float y , float ancho, float alto){
        if (Mundo.debugMode) sr.rect(x, y, ancho, alto);
        sr.triangle(x, y + alto / 2, x + ancho, y + alto / 2, x + ancho / 2, y + alto);
        sr.triangle(x, y + alto / 2, x + ancho, y + alto / 2, x + ancho / 2, y);
    }

    public static void pintarEquis(ShapeRenderer sr, float x, float y , float ancho, float alto){
        // Línea horizontal en el centro
        sr.line(x, y, x + ancho, y + alto);
        // Línea vertical en el centro
        sr.line(x + ancho, y, x, y + alto);

    }
    public static void pintarRectangulo(ShapeRenderer sr, float x, float y , float ancho, float alto){
        sr.rect(x - ancho / 2, y, ancho * 2, alto);
    }

}
