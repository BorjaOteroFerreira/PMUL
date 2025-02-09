package io.github.disparos.entidades;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.disparos.ResourceManager;
import io.github.disparos.mundo.Mundo;

public class Pistola extends Personaje {
    private static float anchoImagen = 1168;
    private static float altoImagen = 749;
    private static float proporcionAlto = 0.05f;
    private static float proporcionAncho = 0.05f;


    public Pistola() {
        super( anchoImagen * proporcionAncho,altoImagen * proporcionAlto, 0 , 600 ,  Estado.PARADO , 120);
        x = hitbox.x = 0;
        y = hitbox.y = Mundo.ALTO / 2;
    }

    public void update(float delta) {
        if (super.getEstado() == Estado.ADELANTE) {
            if(y < Mundo.ALTO - alto)
                y += velocidad * delta;
        } else if (super.getEstado() == Estado.ATRAS) {
            if(y > 0 ){
                y -= velocidad * delta;
            }
        }
    }


    public void moverArriba() {
        super.setEstado(Estado.ADELANTE);
    }

    public void moverAbajo() {
        super.setEstado(Estado.ATRAS);
    }


    public void dibuja(SpriteBatch sb , ShapeRenderer sr) {
        sb.draw(ResourceManager.pistola, x, y, ancho, alto);
        sr.rect(hitbox.x, hitbox.y, ancho, alto);
    }

}
