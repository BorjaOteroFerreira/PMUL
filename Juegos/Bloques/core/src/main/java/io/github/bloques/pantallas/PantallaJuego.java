package io.github.bloques.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

    public PantallaJuego(MainGame game) {
        super(game);
        lineasBloques = new Array<>();
        if (ResourceManager.assetsCargados()) {
            ResourceManager.asignarRecursos();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        tiempoEntreLineas -= aumentoVelocidad;
        // Actualización
        stateTime += delta;
        stateTimeProximaLinea += delta;

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
            }
        }

        // Limpieza de pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1f);

        // Actualizar cámara
        camara.update();
        game.getSpriteBatch().setProjectionMatrix(camara.combined);
        game.getShapeRenderer().setProjectionMatrix(camara.combined);

        // Renderizado
        sr = game.getShapeRenderer();

        // Dibujar bloques rellenos
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.2f, 0.6f, 1f, 1f);
        for (LineaBloques linea : lineasBloques) {
            linea.render(null, sr);
        }
        sr.end();

        // Dibujar bordes
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

        // Dibujar números
        SpriteBatch sb = game.getSpriteBatch();
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

    @Override
    public void show() {
        super.show();
        ResourceManager.cargarRecursos();
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
                        if (bloque1.getNumero() == bloque2.getNumero() && bloque1 != bloque2) {
                            bloque1.eliminar();
                            bloque2.eliminar();
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
}


