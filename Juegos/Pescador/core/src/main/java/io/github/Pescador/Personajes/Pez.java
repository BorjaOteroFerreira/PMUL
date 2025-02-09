package io.github.Pescador.Personajes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;
import io.github.Pescador.Assets;
import io.github.Pescador.Mundo;

public class Pez extends Personaje {
    public enum tipoPez { AZUL,LILA,AMARILLO }
    public static Pez creaNuevoPez() {
        switch(random.nextInt(tipoPez.values().length)) {
            case 1: return new PezAnimado(Assets.aniPezLila);
            case 2: return new PezAnimado(Assets.aniPezAmarillo);
            default: return new Pez(Assets.pez);
        }
    }
    static final int VELOCIDAD_MIN=30,VELOCIDAD_MAX=120;
    static final int ANCHO=30,ALTO =20;
    private static Random random=new Random();
    private boolean pescado;
    protected TextureRegion texture;
    protected Pez(TextureRegion texture) {
        super(0,random.nextInt(Mundo.ALTO_LINEA_MAR- ALTO),
            ANCHO,ALTO,
            VELOCIDAD_MIN+random.nextInt(VELOCIDAD_MAX-VELOCIDAD_MIN),
            random.nextBoolean()?Estado.ADELANTE:Estado.ATRAS);
        this.texture=texture;
        x=estado==Estado.ADELANTE?-ancho:Mundo.ANCHO;
        pescado=false;
        hitBox.y=y+hitBox.height*.25f;
        hitBox.height*=.3f;
        float tercioAncho=hitBox.width/3;
        hitBox.width=tercioAncho;
        hitBox.x=x;
        if(estado==Estado.ADELANTE) hitBox.x+=2*tercioAncho;
    }
    public void actualiza(float delta) {
        float incrementoEspacio=velocidad*delta;
        switch (estado) {
            case ADELANTE:
                x+=incrementoEspacio;
                hitBox.x+=incrementoEspacio;
                if (x>Mundo.ANCHO) Mundo.pezFueraDelMundo(this);
                break;
            case ATRAS:
                x-=incrementoEspacio;
                hitBox.x-=incrementoEspacio;
                if(x<-ancho) Mundo.pezFueraDelMundo(this);
                break;
        }
    }


    public void dibuja(SpriteBatch sb, ShapeRenderer sr) {
        if(estaPescado())
            sb.draw(texture,x,y-alto,ancho/2,0,alto,ancho,1,1,.45f,true);
        else {
            if (estado == Estado.ADELANTE)
                sb.draw(texture, x + ancho, y, -ancho, alto);
            else
                sb.draw(texture, x, y, ancho, alto);
            if(Mundo.MODO_DEBUG)
                sr.rect(hitBox.x,hitBox.y,hitBox.width,hitBox.height);
        }
    }
    public void setPosicionXAnzuelo(float x) { this.x=hitBox.x=x; }
    public void setPosicionYAnzuelo(float y) { this.y=hitBox.y=y; }
    public void setPescado() { pescado=true; para(); }
    public boolean estaPescado() { return pescado; }
}

