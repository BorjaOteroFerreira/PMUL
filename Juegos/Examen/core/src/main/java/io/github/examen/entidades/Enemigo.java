package io.github.examen.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import io.github.examen.ResourceManager;
import io.github.examen.mundo.Mundo;

public class Enemigo extends Entidad {
    int numVidas = 3;
    boolean derecha = true;
    public boolean tocado = false;

    public Enemigo(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
        Random rnd = new Random();
        int tipo = rnd.nextInt(1,3);
        switch(tipo){
            case 1 : super.tipo = Tipo.CUADRADO;break;
            case 2 : super.tipo = Tipo.CIRCULO; break;
            case 3 : super.tipo = Tipo.CRUZ; break;
        }
    }

    public void update(float delta) {
        if (super.getEstado() == Estado.ADELANTE) {
            x = hitbox.x -= velocidad * delta;
        }
        if (super.getEstado() == Estado.ATRAS) {
            x = hitbox.x += velocidad * delta;
        }
        // Make sure hitbox is fully updated with both x and y
        hitbox.setPosition(x, y);
    }
    public void reset() {
        super.setEstado(Estado.PARADO);
        tocado = false;
        x = hitbox.x = Mundo.anchoJuego + ancho;
        y = hitbox.y = new Random().nextInt((int) Mundo.altoRecord, (int) Mundo.ALTO);
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
