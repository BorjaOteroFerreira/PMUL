package io.github.disparos.entidades;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.disparos.ResourceManager;
import io.github.disparos.mundo.Mundo;

public class Pistola extends Personaje {
    private static float anchoImagen = 1168;
    private static float altoImagen = 749;
    private static float proporcionAlto = 0.05f;
    private static float proporcionAncho = 0.05f;
    private int maxBalas = 100;
    private Array<Bala> cargador ;

    public Pistola() {
        super( anchoImagen * proporcionAncho,altoImagen * proporcionAlto, 0 , 600 ,  Estado.PARADO , 120);
        x = hitbox.x = 0;
        y = hitbox.y = Mundo.ALTO / 2;
        cargador = new Array<>();
        for(int i = 0 ; i < maxBalas ; i++){
            Bala bala = new Bala();
            bala.setX(ancho);
            bala.setY( (y + alto / 2)  - bala.alto / 2);
            cargador.add(bala);
        }
    }



    public void disparar() {
        for (Bala bala : cargador) {
            if (bala.getEstado() == Estado.PARADO) {
                bala.setEstado(Estado.ADELANTE);
                bala.setX(x + ancho);
                bala.setY(y + alto  - bala.alto );
                break;
            }
        }
    }
    public void moverArriba() {
        super.setEstado(Estado.ADELANTE);
    }

    public void moverAbajo() {
        super.setEstado(Estado.ATRAS);
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
        for (Bala bala : cargador) {
            bala.update(delta);
        }
    }

    public void render(SpriteBatch sb , ShapeRenderer sr) {
        for (Bala bala : cargador) {
            bala.render(sb, sr);
        }
        sb.draw(ResourceManager.pistola, x, y, ancho, alto);
        sr.rect(hitbox.x, hitbox.y, ancho, alto);
    }

    public Array<Bala> getCargador() {
        return cargador;
    }
}
