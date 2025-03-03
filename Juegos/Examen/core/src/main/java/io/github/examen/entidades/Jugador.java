package io.github.examen.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.examen.Formas;
import io.github.examen.mundo.Mundo;

public class Jugador extends Entidad {
    float tamMax = ancho * 2;
    float tamMin = ancho / 2;
    float aumento = 0.09f ;
    boolean aumentar = true;

    public Jugador(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
        super.tipo = Tipo.CUADRADO;
    }

    public void cambiarForma(){
        switch(tipo){
            case CUADRADO: tipo = Tipo.CIRCULO; break;
            case CIRCULO: tipo = Tipo.CRUZ; break;
            case CRUZ: tipo = Tipo.CUADRADO; break;
        }
    }

    public void render(ShapeRenderer sr) {
        switch (tipo) {
            case CUADRADO: Formas.pintarCuadrado(sr, x, y, ancho, alto); break;
            case CIRCULO: Formas.pintarCirculo(sr, x, y, ancho, alto); break;
            case CRUZ: Formas.pintarCruz(sr, x, y, ancho, alto); break;
        }
        // Mantener el jugador en el centro
        x = Mundo.anchoJuego / 2 - ancho / 2;
        hitbox.setPosition(x, y);
    }

    public void moverArriba() {
        super.setEstado(Estado.ADELANTE);
    }
    public void moverAbajo() {
        super.setEstado(Estado.ATRAS);
    }

    public void movimientoAumentarDisminuir() {
        // Verificar límites antes de cambiar el tamaño
        float newAncho = aumentar ? ancho + aumento : ancho - aumento;
        float newAlto = aumentar ? alto + aumento : alto - aumento;
        float newX = aumentar ? x - aumento / 2 : x + aumento / 2;
        float newY = aumentar ? y - aumento / 2 : y + aumento / 2;
        // Verificar límites antes de aplicar cambios
        boolean dentroDeLimites = (newY >= Mundo.yJuego) && (newY + newAlto <= Mundo.ALTO);
        if (aumentar) {
            if (ancho < tamMax && dentroDeLimites) {
                ancho = newAncho;
                alto = newAlto;
                x = hitbox.x = newX;
                y = hitbox.y = newY;
            } else {
                aumentar = false;
            }
        } else {
            if (ancho > tamMin && dentroDeLimites) {
                ancho = newAncho;
                alto = newAlto;
                x = hitbox.x = newX;
                y = hitbox.y = newY;
            } else {
                aumentar = true;
            }
        }
        hitbox.setSize(ancho, alto);
        hitbox.setPosition(x, y);
    }

    public void update(float delta) {
        movimientoAumentarDisminuir();
        if (super.getEstado() == Estado.ADELANTE) {
            if(y < Mundo.ALTO - alto + alto / 2)
                y = hitbox.y += velocidad * delta;
        } else if (super.getEstado() == Estado.ATRAS) {
            if(y > Mundo.yJuego)
                y = hitbox.y -= velocidad * delta;
        }
        hitbox.setPosition(x, y);
    }

    public void reset(){
        y = Mundo.altoJuego / 2;
        x = Mundo.anchoJuego / 2;
        super.estado = Estado.PARADO;
    }
}
