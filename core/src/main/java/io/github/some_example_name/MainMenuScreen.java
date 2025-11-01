package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private final Drop game;
    private final Texture backgroundTexture;

    public MainMenuScreen(Drop game) {
        this.game = game;
        this.backgroundTexture = new Texture("background.png");
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

        // Nie ładuj tekstury w render() – tylko raz w create()!
        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        // Użyj GlyphLayout do pomiaru rozmiaru tekstu
        GlyphLayout layout1 = new GlyphLayout(game.getFont(), "Witaj w grze Drop");
        GlyphLayout layout2 = new GlyphLayout(game.getFont(), "Start");

        // Wylicz środek ekranu
        float centerX = worldWidth / 2f;
        float centerY = worldHeight / 2f;

        // Narysuj pierwszy tekst wyśrodkowany poziomo
        game.getFont().draw(
            game.getBatch(),
            layout1,
            centerX - layout1.width / 2f,      // środek poziomy
            centerY + layout1.height / 2f + 1  // trochę wyżej (żeby było centralnie)
        );

        // Drugi tekst pod spodem
        game.getFont().draw(
            game.getBatch(),
            layout2,
            centerX - layout2.width / 2f,
            centerY - layout1.height            // odsuń trochę w dół
        );

        game.getBatch().end();

        if (Gdx.input.isTouched()) {
            this.game.setScreen(new GameScreen(game));
            this.dispose();
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
