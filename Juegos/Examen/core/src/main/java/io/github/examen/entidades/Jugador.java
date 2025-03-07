package io.github.examen.entidades;
import static io.github.examen.entidades.Entidad.Estado.*;
import static io.github.examen.entidades.Entidad.Tipo.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.examen.Formas;
import io.github.examen.mundo.Mundo;

public class Jugador extends Entidad {
    float tamMax = ancho * 2;
    float tamMin = ancho / 2;
    float aumento = 0.09f ;
    boolean aumentar = true;

    public Jugador(float x, float y, float ancho, float alto, float velocidad) {
        super(x, y, ancho, alto, velocidad);
        super.tipo = CUADRADO;
    }

    public void update(float delta) {
        movimientoAumentarDisminuir();
        if (super.getEstado() == ADELANTE) {
            if(y < Mundo.ALTO - alto + alto / 2)
                y = hitbox.y += velocidad * delta;
        } else if (super.getEstado() == ATRAS) {
            if(y > Mundo.yJuego)
                y = hitbox.y -= velocidad * delta;
        }
    }

    public void render(ShapeRenderer sr) {
        switch (tipo) {
            case CUADRADO: Formas.pintarCuadrado(sr, x, y, ancho, alto); break;
            case CIRCULO: Formas.pintarCirculo(sr, x, y, ancho, alto); break;
            case CRUZ: Formas.pintarCruz(sr, x, y, ancho, alto); break;
            case RECTANGULO: Formas.pintarRectangulo(sr, x, y, ancho, alto); break;
        }
        // Mantener el jugador en el centro
        x = hitbox.x = Mundo.anchoJuego / 2 - ancho / 2;
    }


    public void cambiarForma(){
        switch(tipo){
            case CUADRADO: tipo = CIRCULO; break;
            case CIRCULO: tipo = CRUZ; break;
            case CRUZ: tipo = RECTANGULO ; break;
            case RECTANGULO: tipo = CUADRADO; break;
        }
    }

    public void moverArriba() {
        super.setEstado(ADELANTE);
    }
    public void moverAbajo() {
        super.setEstado(ATRAS);
    }

    public void movimientoAumentarDisminuir() {
        // Calcular nuevas dimensiones
        float newAncho = aumentar ? ancho + aumento : ancho - aumento;
        float newAlto = aumentar ? alto + aumento : alto - aumento;
        float newX = aumentar ? x - aumento / 2 : x + aumento / 2;
        float newY = aumentar ? y - aumento / 2 : y + aumento / 2;
        // Verificar límites antes de aplicar cambios
        boolean dentroDeLimites = (newY >= Mundo.yJuego) && (newY + newAlto <= Mundo.ALTO);
        boolean tamanioValido = aumentar ? (ancho < tamMax) : (ancho > tamMin);
        if (tamanioValido) {
            // Aplicar cambios
            ancho = newAncho;
            alto = newAlto;
            if (dentroDeLimites) {
                x = hitbox.x = newX;
                y = hitbox.y = newY;
            } else if (newY < Mundo.yJuego) {
                // Reposicionar si sale por el borde inferior
                y = hitbox.y = Mundo.yJuego;
            } else if (newY + newAlto > Mundo.ALTO) {
                // Reposicionar si sale por el borde superior
                y = hitbox.y = Mundo.ALTO - newAlto;
            }
        } else {
            // Cambiar dirección de escalado
            aumentar = !aumentar;
        }
    }

    public void reset(){
        y = Mundo.altoJuego / 2;
        x = Mundo.anchoJuego / 2;
        super.estado = PARADO;
    }
}
