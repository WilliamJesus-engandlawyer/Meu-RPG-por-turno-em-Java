package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BattleScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Personagem jogador;
    private Personagem inimigo;
    private Batalha batalha;
    private Ataque ataqueBasico;

    private EsquivaReflexo esquiva;
    private boolean emEsquiva = false;

    public BattleScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        jogador = new Personagem("Herói", 100, 15);
        inimigo = new Personagem("Goblin", 80, 10);

        batalha = new Batalha(jogador, inimigo);
        ataqueBasico = new Ataque("Espadada", jogador.ataque);
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        batch.begin();

        font.draw(batch, "HP " + jogador.nome + ": " + jogador.hp, 50, 400);
        font.draw(batch, "HP " + inimigo.nome + ": " + inimigo.hp, 50, 370);
        font.draw(batch, batalha.mensagem, 50, 330);

        if (batalha.batalhaEncerrada()) {
            font.draw(batch, batalha.resultado(), 50, 290);
        }

        if (emEsquiva && esquiva != null) {
            esquiva.desenhar(batch, font);
        }

        batch.end();
    }

    public void update(float delta) {
        if (batalha.batalhaEncerrada()) return;

        if (batalha.turnoDoJogador) {
            if (Gdx.input.justTouched()) {
                batalha.turnoJogador(ataqueBasico);
            }
        } else if (!emEsquiva) {
            // Início da esquiva
            esquiva = new EsquivaReflexo();
            emEsquiva = true;
        } else if (esquiva != null) {
            esquiva.atualizar(delta);

            if (esquiva.finalizado()) {
                if (esquiva.esquivouComSucesso()) {
                    batalha.mensagem = jogador.nome + " esquivou do ataque!";
                } else {
                    Ataque ataque = new Ataque("Investida Sombria", inimigo.ataque);
                    ataque.aplicar(jogador);
                    batalha.mensagem = inimigo.nome + " atacou com " + ataque.nome + "!";
                }
                emEsquiva = false;
                batalha.turnoDoJogador = true;
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
