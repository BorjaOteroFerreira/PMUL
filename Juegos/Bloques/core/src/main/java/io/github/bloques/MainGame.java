package io.github.bloques;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends ApplicationAdapter {
    private static SpriteBatch batch;
    private static ShapeRenderer shapeRenderer;
    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }


    public static SpriteBatch getSpriteBatch() {
        return batch;
    }
    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
