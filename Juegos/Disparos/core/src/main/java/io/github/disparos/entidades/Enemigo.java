package io.github.disparos.entidades;

import static io.github.disparos.pantallas.PantallaJuego.pistola;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import io.github.disparos.mundo.Mundo;

public class Enemigo extends Personaje  {
    private static float anchoImagen = 1168 ;
    private static float altoImagen  =768 ;
    private static float proporcionAncho = 0.1f;
    private static float proporcionAlto = 0.1f;




    public Enemigo(){
        super(anchoImagen * proporcionAncho,altoImagen * proporcionAlto, 0 , 600 ,  Estado.ADELANTE , 120);
        y = hitbox.y = new Random().nextInt(0, (int)Mundo.ALTO);
        x = hitbox.x = Mundo.ANCHO + ancho ;
    }

    public void update(float delta) {
        if (super.getEstado() == Estado.ADELANTE) {
            if(x > pistola.ancho)
                x -= velocidad * delta;
        }
    }

    public void render(SpriteBatch sb , ShapeRenderer sr){
        //sb.draw(ResourceManager.enemigo, x, y, ancho, alto);
        sr.rect(x, y, ancho, alto);

    }




}
