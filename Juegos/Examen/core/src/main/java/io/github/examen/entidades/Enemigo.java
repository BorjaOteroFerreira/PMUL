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

    public Enemigo(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
        Random rnd = new Random();
        int tipo =rnd.nextInt(1,3);
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
        if(super.getEstado() == Estado.ATRAS){
           x =  hitbox.x += velocidad * delta;
        }
    }

    public void reset() {
        super.setEstado(Estado.PARADO);
        x = hitbox.x = Mundo.ANCHO + ancho;
        y = hitbox.y = new Random().nextInt(0, (int) Mundo.ALTO);
    }

    public void render(ShapeRenderer sr, SpriteBatch sb) {
        //Dibujar enemigo segun su tipo
        SpriteBatch sprite = sb;
        if (estado != Estado.PARADO) {
            switch (tipo){
                case CUADRADO:
                    sr.rect( x, y, ancho, alto);
                    ResourceManager.fuente.draw(sprite,""+ numVidas ,x , y );
                    break;
                case CIRCULO:
                    sr.circle(x, y , ancho );
                    ResourceManager.fuente.draw(sprite,""+ numVidas ,x, y);
                    break;
                case CRUZ:
                    sr.rect(x,y,5,0);
                    sr.rect(x,y, 0,5);
                    break;
            }
        }
    }



    public int getVidas() {
        return numVidas;
    }

    public void restarVida() {
        numVidas--;
    }
}
