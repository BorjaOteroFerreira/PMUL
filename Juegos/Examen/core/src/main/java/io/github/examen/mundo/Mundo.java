package io.github.examen.mundo;

public class Mundo {
    public static float ANCHO = 1280;
    public static float ALTO = 720;
    public static int numColisiones = 0;
    public static float altoRecord = ALTO / 5;
    public static float anchoRecord = ANCHO;
    public static float anchoJuego = ANCHO;
    public static float altoJuego = ALTO -  altoRecord;
    public static float xRecord = 0;
    public static float yRecord = 0;
    public static float yJuego = altoRecord;
    public static void setNumColisiones(int colisiones) {
        numColisiones = colisiones;
    }
}
