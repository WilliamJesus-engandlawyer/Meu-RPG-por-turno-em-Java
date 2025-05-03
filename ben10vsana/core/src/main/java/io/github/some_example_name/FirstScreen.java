package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;
    private int selectedOption = 0; // 0 = Começar, 1 = Sair

    public FirstScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Meu RPG por Turnos", 100, 400);

        if (selectedOption == 0) {
            font.draw(batch, "> Começar", 100, 300);
            font.draw(batch, "  Sair", 100, 250);
        } else {
            font.draw(batch, "  Começar", 100, 300);
            font.draw(batch, "> Sair", 100, 250);
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = 1 - selectedOption;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedOption == 0) {
                game.setScreen(new BattleScreen(game)); // vai para a batalha
            } else {
                Gdx.app.exit();
            }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
