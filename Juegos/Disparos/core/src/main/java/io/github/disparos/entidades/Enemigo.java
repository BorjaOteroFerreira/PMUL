package io.github.disparos.entidades;

import static io.github.disparos.pantallas.PantallaJuego.pistola;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.nio.channels.FileLock;
import java.util.Random;

import io.github.disparos.ResourceManager;
import io.github.disparos.mundo.Mundo;

public class Enemigo extends Personaje  {
    private static float anchoImagen = 225 ;
    private static float altoImagen  =225 ;
    private static float proporcionAncho = 0.1f;
    private static float proporcionAlto = 0.1f;

    public Enemigo(){
        super(anchoImagen * proporcionAncho,altoImagen * proporcionAlto, 0 , 600 ,  Estado.ADELANTE , 120);
        y = hitbox.y = new Random().nextInt(0, (int)Mundo.ALTO);
        x = hitbox.x = Mundo.ANCHO + ancho ;
    }

    public void update(float delta) {
        if (super.getEstado() == Estado.ADELANTE) {
            if(x > pistola.ancho) {
                x -= velocidad * delta;
            }
        }
    }

    public void reset(){
        super.setEstado(Estado.PARADO);
        x = hitbox.x = Mundo.ANCHO + ancho;
        y = hitbox.y = 0;
    }

    public void render(SpriteBatch sb , ShapeRenderer sr) {
        if(estado == Estado.ADELANTE){
            sb.draw(ResourceManager.enemigo, x, y, ancho, alto);
            //sr.rect(x, y, ancho, alto);
        }
    }
    @Override
    public Rectangle getHitbox(){
        hitbox.x = x;
        hitbox.y = y;
        return super.getHitbox();
    }
}
