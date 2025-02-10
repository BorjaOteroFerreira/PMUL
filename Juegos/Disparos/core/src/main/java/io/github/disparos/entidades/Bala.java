package io.github.disparos.entidades;
public class Bala extends Personaje {
    private static float anchoImagen = 400;
    private static float altoImagen = 200;
    private static float proporcionAncho = 0.1f;
    private static float proporcionAlto = 0.1f;



    public Bala(float x , float y) {
        super (anchoImagen * proporcionAncho,altoImagen * proporcionAlto, 0 , 600 ,  Personaje.Estado.ADELANTE , 120);
        x = hitbox.x = 0;
        y = hitbox.y = 0;
    }

    public void mover(float delta) {

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
