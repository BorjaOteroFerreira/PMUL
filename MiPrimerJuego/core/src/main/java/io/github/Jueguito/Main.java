package io.github.Jueguito;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    float x = 0;
    boolean derecha = true;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        if( x <= 640 - image.getWidth() && x > 0 - image.getWidth() && derecha) {
            x++;
        }else {
            derecha = x <= 1 ? true : false;
            x--;
        }
        System.out.println(x);
        batch.draw(image, x, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
