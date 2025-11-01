package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    private SpriteBatch spriteBatch;
    private FitViewport fitViewport;
    private Texture backgroundTexture;
    private Texture bucketTexture;
    private Texture dropTexture;
    private Sound dropSound;
    private Music music;
    private Sprite bucketSprite;
    private Array<Sprite> dropSprites;
    private Vector2 touchPos;
    private float dropTimer;
    private Rectangle bucketRectangle;
    private Rectangle dropRectangle;

    @Override
    public void create() {
        this.backgroundTexture = new Texture("background.png");
        this.bucketTexture = new Texture("bucket.png");
        this.bucketSprite = new Sprite(bucketTexture);
        this.bucketSprite.setSize(1, 1);
        this.dropTexture = new Texture("drop.png");
        this.dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        this.spriteBatch = new SpriteBatch();
        this.fitViewport = new FitViewport(8, 5);
        this.touchPos = new Vector2();
        this.dropSprites = new Array<>();
        this.bucketRectangle = new Rectangle();
        this.dropRectangle = new Rectangle();
        music.setLooping(true);
        music.setVolume(.2f);
        music.play();
    }

    @Override
    public void render() {
        this.input();
        this.logic();
        this.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.fitViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }

    private void draw() {
        float worldHeight = fitViewport.getWorldHeight();
        float worldWidth = fitViewport.getWorldWidth();

        ScreenUtils.clear(Color.BLACK);
        fitViewport.apply();
        spriteBatch.setProjectionMatrix(fitViewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        bucketSprite.draw(spriteBatch);

        for (Sprite dropSprite : dropSprites){
            dropSprite.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    private void logic() {
        float worldWidth = fitViewport.getWorldWidth();
        float bucketWidth = bucketSprite.getWidth();
        float delta = Gdx.graphics.getDeltaTime();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketSprite.getWidth(), bucketSprite.getHeight());

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropSprite.getWidth(), dropSprite.getHeight());

            if (dropSprite.getY() < -dropHeight) {
                dropSprites.removeIndex(i);
            } else if (bucketRectangle.overlaps(dropRectangle)) {
                dropSprites.removeIndex(i);
                dropSound.play();
            }
        }


        dropTimer += delta;
        if (dropTimer > 1f){
            dropTimer = 0;
            this.createDroplet();
        }
    }

    private void input() {

        float speed = 3f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            bucketSprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bucketSprite.translateX(-speed * delta);
        }

        if (Gdx.input.isTouched()) {
            this.touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            fitViewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }

    private void createDroplet(){
        float dropWidth = 1;
        float dropHeight = 1;
        float worldHeight = fitViewport.getWorldHeight();
        float worldWidth = fitViewport.getWorldWidth();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);
        this.dropSprites.add(dropSprite);
    }
}
