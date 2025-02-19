package io.github.bloques.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.bloques.MainGame;
import io.github.bloques.ResourceManager;
import io.github.bloques.entidades.Bloque;
import io.github.bloques.entidades.LineaBloques;
import io.github.bloques.mundo.Mundo;

public class PantallaJuego extends Pantalla {
    private float stateTime = 0;
    private static float tiempoEntreLineas = 3.5f;
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private float stateTimeProximaLinea = tiempoEntreLineas;
    private Array<LineaBloques> lineasBloques;
    private Bloque bloque1;
    private Bloque bloque2;
    private float aumentoVelocidad = 0.0001f;
    public static int puntos = 0;
    Preferences prefs ;
    public boolean recursosAsignados = false;

    public PantallaJuego(MainGame game) {
        super(game);
        lineasBloques = new Array<>();

        prefs = Gdx.app.getPreferences("Records");
    }


    @Override
    public void show() {
        super.show();
        ResourceManager.bso.loop(1f);
    }

    public void eliminarLineasVacias(){
        // Eliminar líneas con todos los bloques destruidos
        for (int i = lineasBloques.size - 1; i >= 0; i--) {
            LineaBloques linea = lineasBloques.get(i);
            boolean todosDestruidos = true;
            for (Bloque bloque : linea.getBloques()) {
                if (!bloque.isDestruido()) {
                    todosDestruidos = false;
                    break;
                }
            }
            if (todosDestruidos) {
                lineasBloques.removeIndex(i);
            }
        }
    }



    @Override
    public void render(float delta) {

        super.render(delta);
        tiempoEntreLineas -= aumentoVelocidad;
        stateTime += delta;
        stateTimeProximaLinea += delta;
        eliminarLineasVacias();
        crearNuevaLinea();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1f);
        camara.update();
        game.getSpriteBatch().setProjectionMatrix(camara.combined);
        game.getShapeRenderer().setProjectionMatrix(camara.combined);
        sr = game.getShapeRenderer();
        sb = game.getSpriteBatch();
        if(Mundo.DEBUG){
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sb.begin();
            for (LineaBloques linea : lineasBloques) {
                for (Bloque bloque : linea.getBloques()) {
                    if (!bloque.isDestruido()) {
                        //sr.setColor(bloque.getColor());
                        sb.draw(bloque.getGelatina(), bloque.x, bloque.y, bloque.ancho, bloque.alto);
                        //sr.rect(bloque.x, bloque.y, bloque.ancho, bloque.alto);
                    }
                }
            }
            sb.end();
            sr.end();
        }else{
            // Dibujar números
            sb.begin();
            for (LineaBloques linea : lineasBloques) {
                for (Bloque bloque : linea.getBloques()) {
                    if (!bloque.isDestruido()) {
                        ResourceManager.fuente.draw(sb, String.valueOf(bloque.getNumero()),
                            bloque.x + bloque.ancho / 2 - 10,
                            bloque.y + bloque.alto / 2 + 10);
                    }
                }
            }
            sb.end();
        }
       //dibujarBordes();
    }

    public void dibujarBordes(){
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(0f, 0f, 0f, 1f);
        for (LineaBloques linea : lineasBloques) {
            for (Bloque bloque : linea.getBloques()) {
                if (!bloque.isDestruido()) {
                    sr.rect(bloque.x, bloque.y, bloque.ancho, bloque.alto);
                }
            }
        }
        sr.end();
    }
    public void crearNuevaLinea(){
        // Crear nueva línea cuando corresponda
        if (stateTimeProximaLinea >= tiempoEntreLineas) {
            LineaBloques linea = new LineaBloques(0, 0, ANCHO, ALTO / Mundo.NUM_LINEAS_POR_PANTALLA);
            lineasBloques.insert(0, linea); // Añadir al principio del array
            stateTimeProximaLinea = 0;
            // Actualizar posición de todas las líneas
            float posY = 0;
            for (LineaBloques lineaExistente : lineasBloques) {
                for (Bloque bloque : lineaExistente.getBloques()) {
                    bloque.y = posY;
                    bloque.hitbox.y = posY;
                }
                posY += ALTO / Mundo.NUM_LINEAS_POR_PANTALLA;
                if(posY >= ALTO){
                    game.mostrarPantallaFin();
                }
            }
        }
    }



    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 coordenadas = new Vector3(screenX, screenY, 0);
        camara.unproject(coordenadas);
        for (LineaBloques linea : lineasBloques) {
            for (Bloque bloque : linea.getBloques()) {
                if (!bloque.isDestruido() && bloque.hitbox.contains(coordenadas.x, coordenadas.y)) {
                    if (bloque1 == null) {
                        bloque1 = bloque;
                        return true;
                    } else {
                        bloque2 = bloque;
                        Boolean condicionEliminar = Mundo.DEBUG ? bloque1.getGelatina().equals(bloque2.getGelatina()) && bloque1 != bloque2 :
                                                                  bloque1.getNumero() == bloque2.getNumero() && bloque1 != bloque2;
                        if(condicionEliminar){
                                bloque1.eliminar();
                                bloque2.eliminar();
                                puntos++;
                        }
                        bloque1 = null;
                        bloque2 = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void reset(){
        lineasBloques.clear();
        tiempoEntreLineas = 3.5f;
        stateTimeProximaLinea = tiempoEntreLineas;
        puntos = 0;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}


