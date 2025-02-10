package io.github.disparos.entidades;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.disparos.ResourceManager;
import io.github.disparos.mundo.Mundo;

public class Bala extends Entidad {
    private static float anchoImagen = 300;
    private static float altoImagen = 300;
    private static float proporcionAncho = 0.05f;
    private static float proporcionAlto = 0.05f;

    public Bala() {
        super(anchoImagen * proporcionAncho,
            altoImagen * proporcionAlto,
            0,
            0,
            Estado.PARADO,
            200);
            x = hitbox.x = 0;
            y = hitbox.y =  0;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setEstado(Estado estado) {
        super.setEstado(estado);
    }

    public Estado getEstado() {
        return super.getEstado();
    }

    public void render(SpriteBatch sb, ShapeRenderer sr) {
        if(estado == Estado.ADELANTE) {
            //sr.rect(x, y, ancho, alto);
            sb.draw(ResourceManager.bala, x, y, ancho, alto);
        }
    }

    public void reset(){
        super.setEstado(Estado.PARADO);
        x = 0;
        y = 0;
    }

    @Override
    public Rectangle getHitbox(){
        hitbox.x = x;
        hitbox.y = y;
        return hitbox;
    }

    public void update(float delta) {
        if (super.getEstado() == Estado.ADELANTE) {
            if (x < Mundo.ANCHO){
                hitbox.setPosition(x,y);
                x += velocidad * delta;
            }else{
                super.setEstado(Estado.PARADO);
            }
        }
    }
}
