package io.github.examen.entidades;

import static io.github.examen.entidades.Entidad.Tipo.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import io.github.examen.Formas;
import io.github.examen.ResourceManager;
import io.github.examen.mundo.Mundo;

public class Enemigo extends Entidad {
    public int numVidas ;
    boolean derecha = true;
    public boolean tocado = false;

    public Enemigo(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
        Random rnd = new Random();
        int tipo = rnd.nextInt(3) + 1;
        switch(tipo){
            case 1 : super.tipo = CRUZ; numVidas = tipo; break;
            case 2 : super.tipo = CIRCULO; numVidas = tipo; break;
            case 3 : super.tipo = CUADRADO; numVidas = tipo ; break;
        }
    }

    public void update(float delta) {
        switch (super.getEstado()) {
            case ATRAS: moverIzquierda(delta); break;
            case ADELANTE: moverDerecha(delta); break;
        }
    }


    public void render(ShapeRenderer sr, SpriteBatch sb) {
        SpriteBatch sprite = sb;
        if (estado != Estado.PARADO) {
            switch (tipo){
                case CUADRADO: Formas.pintarCuadrado(sr, x, y, ancho, alto); break;
                case CIRCULO: Formas.pintarCirculo(sr, x, y, ancho, alto); break;
                case CRUZ: Formas.pintarCruz(sr, x, y, ancho, alto); break;
            }
            if(tipo != Tipo.CRUZ){
                ResourceManager.fuente.draw(sprite, "" + numVidas, x + ancho/2 -2, y + alto/2 + 5);
            }
        }
    }

    public void moverIzquierda(float delta){
        x = hitbox.x -= velocidad * delta;
        if (x < 0) { // Rebote en el borde izquierdo
            super.setEstado(Estado.ADELANTE);
            tocado = false;
        }
    }

    public void moverDerecha(float delta){
        x = hitbox.x += velocidad * delta;
        if (x + ancho > Mundo.ANCHO) { // Rebote en el borde derecho
            super.setEstado(Estado.ATRAS);
            tocado = false;
        }
    }

    public void restarVida() {
        numVidas--;
    }

    public void reset() {
        super.setEstado(Estado.PARADO);
        switch(super.tipo){
            case CUADRADO: numVidas = 3 ; break;
            case CIRCULO: numVidas = 2; break;
            case  CRUZ: numVidas = 1; break;
        }
        tocado = false;
        x = hitbox.x = Mundo.anchoJuego + ancho;
        y = hitbox.y = Mundo.yJuego + new Random().nextInt((int)(Mundo.ALTO - Mundo.yJuego));
    }
}
