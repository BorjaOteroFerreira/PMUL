package io.github.Pescador.Personajes;
import io.github.Pescador.Mundo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import io.github.Pescador.Assets;
public class Anzuelo extends Personaje {
    private static float xDesplazamientoAnzuelo = 41;
    private static float Y = 158;
    private static float ANCHO = 10, ALTO = 10;
    private static int VELOCIDAD = 80;
    private static ArrayList<Pez> pescados = null;

    public Anzuelo() {
        super(0, Y, ANCHO, ALTO, VELOCIDAD, Estado.PARADO);
        pescados = new ArrayList<>();
    }

    public void reset() {
        x = Mundo.pescador.x + xDesplazamientoAnzuelo;
        y = Y;
        estado = Estado.PARADO;
    }

    public void actualiza(float delta) {
        switch (estado) {
            case ADELANTE:
                y += velocidad * delta;
                if (y > Y) {
                    para();
                    for (Pez pescado : pescados)
                        Mundo.pezALaCesta(pescado);
                    pescados.clear();
                }
                for (Pez pescado : pescados)
                    pescado.setPosicionYAnzuelo(y);
                break;
            case ATRAS:
                y -= velocidad * delta;
                if (y < 0) adelante();
                break;
        }
        x = hitBox.x = Mundo.pescador.x + xDesplazamientoAnzuelo;
        hitBox.y = y;
    }

    public void dibuja(SpriteBatch sb, ShapeRenderer sr) {
        if (Mundo.MODO_DEBUG) sr.rect(x, y, ancho, alto);
        if (!estaParado()) sb.draw(Assets.sedal, x + ancho / 2, y + alto, 1, Y - y);
        sb.draw(Assets.anzuelo, x, y, ancho, alto);
    }

    public boolean vacio() {
        return pescados.isEmpty();
    }

    public boolean enModoPesca() {
        return estado == Estado.ADELANTE;
    }

    public void pescado(Pez pescado) {
        pescado.setPescado();
        // Centramos el pez en el anzuelo si es el primero, el resto que se vean
        if (vacio()) pescado.setPosicionXAnzuelo(x);
        pescados.add(pescado);
    }

    public int getPescados() {
        return pescados.size();
    }
}
