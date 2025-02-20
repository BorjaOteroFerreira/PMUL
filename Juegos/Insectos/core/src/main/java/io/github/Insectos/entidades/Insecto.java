package io.github.Insectos.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import io.github.Insectos.mundo.Mundo;

public class Insecto extends Entidad {

    private float direccionX, direccionY;
    private int velocidadMin = 100;
    private int velocidadMax = 1000;

    private static final Random random = new Random();

    public Insecto(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
        cambiarDireccion();

    }

    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sr.rect(x,y,ancho,alto);
        sb.draw(imagen,x,y,ancho,alto);
    }


    @SuppressWarnings("NewApi")
    private void cambiarDireccion() {
        float anguloActual = (float) Math.atan2(direccionY, direccionX);
        // Genera un ángulo nuevo que esté entre 90 y 270 grados respecto al ángulo actual
        float anguloNuevo = anguloActual + (float) Math.PI / 2 + random.nextFloat() * (float) Math.PI;
        direccionX = (float) Math.cos(anguloNuevo);
        direccionY = (float) Math.sin(anguloNuevo);
        velocidad = random.nextInt(velocidadMin, velocidadMax);
        // Si el insecto se sale de los límites, lo reposicionamos
        if (x < 0) x = 0;
        if (x > Mundo.ANCHO - ancho) x = Mundo.ANCHO - ancho;
        if (y < 0) y = 0;
        if (y > Mundo.ALTO - alto) y = Mundo.ALTO - alto;
    }
    public void update(float delta) {
        hitbox.setPosition(x,y);
        x += direccionX * velocidad * delta;
        y += direccionY * velocidad * delta;
        if (x <= 0 || x >= Mundo.ANCHO - ancho || y < 0  || y > Mundo.ALTO - alto) {
            cambiarDireccion();
        }

    }

    public void setImagen(Texture imagen) {
        this.imagen = imagen;
    }
}
