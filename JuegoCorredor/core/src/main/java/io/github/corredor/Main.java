package io.github.corredor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture spriteSheet;
    private TextureRegion[] framesRight;
    private TextureRegion[] framesLeft;
    private Animation<TextureRegion> runAnimationRight;
    private Animation<TextureRegion> runAnimationLeft;
    private float stateTime;

    private float x = 0;
    private float speed = 200;
    private boolean facingRight = true;
    private float characterWidth = 94;

    @Override
    public void create() {
        batch = new SpriteBatch();
        spriteSheet = new Texture("sprite_sheet61.png");

        // Create frames for both directions using all 8 frames
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
            spriteSheet.getWidth() / 4,
            spriteSheet.getHeight() / 2);

        // Initialize arrays for 8 frames
        framesRight = new TextureRegion[8];
        framesLeft = new TextureRegion[8];

        // Load first row (first 4 frames)
        for (int i = 0; i < 4; i++) {
            // For left-facing frames (original orientation)
            framesLeft[i] = new TextureRegion(tmp[0][i]);

            // For right-facing frames (flipped)
            framesRight[i] = new TextureRegion(tmp[0][i]);
            framesRight[i].flip(true, false);
        }

        // Load second row (next 4 frames)

        }

        // Create animations with all 8 frames
        runAnimationRight = new Animation<>(0.08f, framesRight);
        runAnimationLeft = new Animation<>(0.08f, framesLeft);
        stateTime = 0f;
    }

    @Override
    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();

        // Update position
        if (facingRight) {
            x += speed * Gdx.graphics.getDeltaTime();
            if (x > Gdx.graphics.getWidth() - characterWidth) {
                facingRight = false;
            }
        } else {
            x -= speed * Gdx.graphics.getDeltaTime() ;
            if (x < 0 - characterWidth / 2) {
                facingRight = true;
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        TextureRegion currentFrame = facingRight ?
            runAnimationRight.getKeyFrame(stateTime, true) :
            runAnimationLeft.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, 50);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        spriteSheet.dispose();
    }
}
