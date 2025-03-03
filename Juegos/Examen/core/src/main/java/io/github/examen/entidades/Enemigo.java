package io.github.examen.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import io.github.examen.ResourceManager;
import io.github.examen.mundo.Mundo;

public class Enemigo extends Entidad {
    int numVidas ;
    boolean derecha = true;
    public boolean tocado = false;

    public Enemigo(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
        Random rnd = new Random();
        int tipo = rnd.nextInt(3) + 1;
        switch(tipo){
            case 1 : super.tipo = Tipo.CUADRADO; numVidas = 2 ; break;
            case 2 : super.tipo = Tipo.CIRCULO; numVidas = 3; break;
            case 3 : super.tipo = Tipo.CRUZ; numVidas = 1; break;
        }
    }

    public void update(float delta) {
        if (super.getEstado() == Estado.ADELANTE) { // Movimiento hacia la izquierda
            x = hitbox.x -= velocidad * delta;
            if (x < 0) { // Rebote en el borde izquierdo
                super.setEstado(Estado.ATRAS);
                tocado = false; // Reiniciar el tocado
            }
        }
        if (super.getEstado() == Estado.ATRAS) { // Movimiento hacia la derecha
            x = hitbox.x += velocidad * delta;
            if (x + ancho > Mundo.ANCHO) { // Rebote en el borde derecho
                super.setEstado(Estado.ADELANTE);
                tocado = false; // Reiniciar el tocado
            }
        }
        hitbox.setPosition(x, y);
    }

    public void reset() {
        super.setEstado(Estado.PARADO);
        tocado = false;
        x = hitbox.x = Mundo.anchoJuego + ancho;
        y = hitbox.y = Mundo.yJuego + new Random().nextInt((int)(Mundo.ALTO - Mundo.yJuego));
    }

    public void render(ShapeRenderer sr, SpriteBatch sb) {
        //Dibujar enemigo segun su tipo
        SpriteBatch sprite = sb;
        if (estado != Estado.PARADO) {
            switch (tipo){
                case CUADRADO:
                    sr.rect(x, y, ancho, alto);
                    break;
                case CIRCULO:
                    sr.rect(x, y, ancho, alto);
                    // Centrar el circulo en la hitbox
                    sr.circle(x + ancho/2, y + alto/2, ancho/2);
                    break;
                case CRUZ:
                    sr.rect(x, y, ancho, alto);
                    // Línea horizontal en el centro
                    sr.line(x, y + alto/2, x + ancho, y + alto/2);
                    // Línea vertical en el centro
                    sr.line(x + ancho/2, y, x + ancho/2, y + alto);
                    break;
            }
            //Centrar el texto en la figura
            ResourceManager.fuente.draw(sprite, "" + numVidas, x + ancho/2 -2, y + alto/2 + 5);
        }
    }

    public int getVidas() {
        return numVidas;
    }

    public void restarVida() {
        numVidas--;
    }
}
