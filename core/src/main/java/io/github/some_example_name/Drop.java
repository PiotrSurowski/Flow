package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Drop extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private FitViewport viewport;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        this.viewport = new FitViewport(8, 5);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public FitViewport getViewport() {
        return viewport;
    }
}
