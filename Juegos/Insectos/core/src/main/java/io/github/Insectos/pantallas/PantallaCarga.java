package io.github.Insectos.pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import io.github.Insectos.MainGame;
import io.github.Insectos.ResourceManager;
import io.github.Insectos.mundo.Mundo;


public class PantallaCarga extends Pantalla {
    private final BitmapFont font = new BitmapFont();
    private boolean cargado = false;
    private float alpha = 1f;
    private State currentState = State.LOADING;
    private float stateTime = 0;
    private static final float TRANSITION_TIME = 1f; // 1 segundo de transición
    private static PantallaCarga instance;

    // Estados posibles de la pantalla de carga
    private enum State {
        LOADING,
        TRANSITIONING,
        ERROR
    }

    private String errorMessage = "";

    public PantallaCarga(MainGame game) {
        super(game);
        initializeLoading();
    }


    private void initializeLoading() {
        try {
            ResourceManager.cargarRecursos();
            currentState = State.LOADING;
            cargado = false;
            alpha = 1f;
            stateTime = 0;
            errorMessage = "";
        } catch (Exception e) {
            handleError("Error initializing resources: " + e.getMessage());
        }
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += delta;

        clearScreen();

        switch (currentState) {
            case LOADING:
                renderLoading();
                checkLoadingComplete();
                break;
            case TRANSITIONING:
                renderTransition(delta);
                break;
            case ERROR:
                renderError();
                break;
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderLoading() {
        SpriteBatch batch = game.getSpriteBatch();
        batch.begin();
        try {
            float progress = ResourceManager.getProgress();
            String loadingText = "Cargando... " + (int) (progress * 100) + "%";
            font.draw(batch, loadingText,
                Mundo.ANCHO / 2f - 50,
                Mundo.ALTO / 2f);
        } catch (Exception e) {
            handleError("Error loading resources: " + e.getMessage());
        }
        batch.end();
    }

    private void renderTransition(float delta) {
        // Actualizar alpha para la transición
        alpha -= delta / TRANSITION_TIME;
        alpha = MathUtils.clamp(alpha, 0f, 1f);

        // Dibujar la pantalla de carga desvaneciéndose
        SpriteBatch batch = game.getSpriteBatch();
        batch.begin();
        font.setColor(1, 1, 1, alpha);
        font.draw(batch, "Cargando... 100%",
            Mundo.ANCHO / 2f - 50,
            Mundo.ALTO / 2f);
        batch.end();

        if (alpha <= 0) {
            try {
                dispose();
                game.cargarPantallaInicio();
            } catch (Exception e) {
                handleError("Error transitioning to game screen: " + e.getMessage());
            }
        }
    }

    private void renderError() {
        SpriteBatch batch = game.getSpriteBatch();
        batch.begin();
        font.setColor(1, 0, 0, 1); // Rojo para el error
        font.draw(batch, errorMessage,
            Mundo.ANCHO / 2f - 100,
            Mundo.ALTO / 2f);
        font.draw(batch, "Presiona SPACE para reintentar",
            Mundo.ANCHO / 2f - 100,
            Mundo.ALTO  / 2f - 30);
        batch.end();
    }

    private void checkLoadingComplete() {
        try {
            if (!cargado && ResourceManager.recursosCargados()) {
                cargado = true;
                ResourceManager.asignarRecursos();
                currentState = State.TRANSITIONING;
                stateTime = 0;
            }
        } catch (Exception e) {
            handleError("Error checking resource loading: " + e.getMessage());
        }
    }

    private void handleError(String message) {
        currentState = State.ERROR;
        errorMessage = message;
        Gdx.app.error("LoadScreen", message);
    }


    @Override
    public boolean keyDown(int keycode) {
        // Si hay un error y se presiona SPACE, reintentar la carga
        if (currentState == State.ERROR && (keycode == Input.Keys.SPACE || keycode == Input.Keys.ESCAPE)) {
            if (keycode == Input.Keys.SPACE)
                initializeLoading();
            else
                Gdx.app.exit();
            return true;
        }
        return super.keyDown(keycode);
    }

    @Override
    public void dispose() {
        font.dispose();
        super.dispose();
    }

    // Método público para reiniciar la pantalla de carga si es necesario
    public void restart() {
        initializeLoading();
    }
}
