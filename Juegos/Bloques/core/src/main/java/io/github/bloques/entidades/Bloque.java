package io.github.bloques.entidades;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.bloques.mundo.Mundo;

public class Bloque extends Entidad {
    protected boolean destruido = false;
    protected boolean primeraFila = true;
    protected int numero;
    protected Texture gelatina;

    public Bloque(float x, float y, float ancho, float alto, int numero, Texture gelatina) {
        super(x, y, ancho, alto);
        this.numero = numero;
        this.gelatina = gelatina;
    }

    public Texture getGelatina() {
        return gelatina;
    }

    public void update() {
        if (!destruido) {
            if (primeraFila) {
                y = 0;
                primeraFila = !primeraFila;
            } else {
                y += Mundo.ALTO / Mundo.NUM_LINEAS_POR_PANTALLA;
            }
            hitbox.setPosition(x, y);
        }
    }

    public void render(SpriteBatch sb, ShapeRenderer sr) {
        if (!destruido) {
            sr.rect(x, y, ancho, alto);
            sb.draw(gelatina, x, y, ancho, alto);
        }

    }

    public void eliminar() {
        destruido = true;
    }

    public boolean isDestruido() {
        return destruido;
    }

    public int getNumero() {
        return numero;
    }
}
