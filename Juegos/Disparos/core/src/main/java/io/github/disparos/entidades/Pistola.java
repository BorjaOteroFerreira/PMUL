package io.github.disparos.entidades;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.disparos.ResourceManager;
import io.github.disparos.mundo.Mundo;

public class Pistola extends Personaje {
    private static float anchoImagen = 1168;
    private static float altoImagen = 749;
    private float ancho;
    private float alto;
    private float x, y;
    private float velocidad;
    private static float proporcionAlto = 0.1f;
    private static float proporcionAncho = 0.1f;
    private int municion;
    private int tiempoRecargaActual;
    private boolean recargando;
    private int municionMaxima;
    //Hitbox
    private Rectangle hitbox;

    public Pistola() {
        super();
        ancho = anchoImagen * proporcionAncho;
        alto = altoImagen * proporcionAlto;
        municion = 10;
        x = 0;
        y = Mundo.ALTO / 2 - alto;
        velocidad = 40f;
        hitbox = new Rectangle(x, y, ancho, alto); // Inicializa la hitbox
    }




    public void actualizar(float delta) {
        if (super.getEstado() == Estado.ADELANTE) {
            y += velocidad * delta;
        } else if (super.getEstado() == Estado.ATRAS) {
            y -= velocidad * delta;
        }
        hitbox.setPosition(x, y);
        super.setEstado(Estado.PARADO);
    }

    public void moverArriba() {
        super.setEstado(Estado.ADELANTE);
    }

    public void moverAbajo() {
        super.setEstado(Estado.ATRAS);
    }

    public int getMunicion() {
        return municion;
    }

    public int getMunicionMaxima() {
        return municionMaxima;
    }

    public void dibuja(SpriteBatch sb) {
        sb.draw(ResourceManager.pistola, x, y, ancho, alto);
    }



}
