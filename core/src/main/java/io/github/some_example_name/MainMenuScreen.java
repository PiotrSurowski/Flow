package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private final Drop game;
    private final Texture backgroundTexture;
    private final Texture startButtonTexture;
    private final Rectangle startButtonBounds;
    private final Vector2 touchPos;

    public MainMenuScreen(Drop game) {
        this.game = game;
        this.backgroundTexture = new Texture("background.png");
        this.startButtonTexture = new Texture("startButton.png");
        this.startButtonBounds = new Rectangle();
        this.touchPos = new Vector2();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float worldWidth = game.getViewport().getWorldWidth();
        float worldHeight = game.getViewport().getWorldHeight();

        ScreenUtils.clear(Color.BLACK);
        game.getViewport().apply();
        game.getBatch().setProjectionMatrix(game.getViewport().getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        // Tekst tytułowy
        GlyphLayout layoutTitle = new GlyphLayout(game.getFont(), "Lzy sasiada");
        float titleX = (worldWidth - layoutTitle.width) / 2f;
        float titleY = worldHeight * 0.75f;
        game.getFont().draw(game.getBatch(), layoutTitle, titleX, titleY);

        // Pozycja przycisku (środek ekranu)
        float buttonWidth = 1.5f;
        float buttonHeight = 1f;
        float buttonX = (worldWidth - buttonWidth) / 2f;
        float buttonY = (worldHeight - buttonHeight) / 2f - 1f;

        // Rysowanie przycisku
        game.getBatch().draw(startButtonTexture, buttonX, buttonY, buttonWidth, buttonHeight);

        // Aktualizacja prostokąta kolizji przycisku
        startButtonBounds.set(buttonX, buttonY, buttonWidth, buttonHeight);

        game.getBatch().end();

        // Obsługa kliknięcia
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.getViewport().unproject(touchPos);

            if (startButtonBounds.contains(touchPos)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        this.game.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
