package io.github.Pescador.Personajes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.Pescador.Assets;
import io.github.Pescador.Mundo;

public class Pescador extends Personaje {
    private static final int VELOCIDAD=80;
    private static final float X=50,Y=128;
    private static final float ANCHO=50,ALTO=50;
    public Pescador() { super(X,Y,ANCHO,ALTO,VELOCIDAD,Estado.PARADO); }
    public void reset() { x=X; y=Y; estado=Estado.PARADO; }
    @Override public void actualiza(float delta) {
        if(estado==Estado.PARADO) return;
        switch (estado) {
            case ADELANTE:
                x+=velocidad*delta;
                float xMax= Mundo.ANCHO-ancho;
                if (x>xMax) x=xMax;
                break;
            case ATRAS:
                x-=velocidad*delta;
                if(x<0) x=0;
                break;
        }
    }
    @Override public void dibuja(SpriteBatch sb, ShapeRenderer sr) {
        sb.draw(Assets.pescador,x,y,ancho,alto);
    }
}

