package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class BattleScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Personagem jogador;
    private Personagem inimigo;
    private Batalha batalha;
    private Ataque ataqueBasico;

    private EsquivaReflexo esquivaWASD;
    private EsquivaReflexoSetas esquivaSetas;
    private boolean emEsquiva = false;
    private boolean usandoSetas = false;

    private Random random = new Random();

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

        // Desenhar esquiva correspondente
        if (emEsquiva) {
            if (usandoSetas && esquivaSetas != null) {
                esquivaSetas.desenhar(batch, font);
            } else if (esquivaWASD != null) {
                esquivaWASD.desenhar(batch, font);
            }
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
            // Sorteia ataque do inimigo
            boolean ataqueBrutal = random.nextBoolean(); // true = ataque com setas

            Ataque ataque = ataqueBrutal ?
                new Ataque("Ataque Brutal", inimigo.ataque + 5) :
                new Ataque("Investida Sombria", inimigo.ataque);

            batalha.ultimoAtaqueInimigo = ataque;

            if (ataqueBrutal) {
                esquivaSetas = new EsquivaReflexoSetas();
                esquivaSetas.iniciar();
                usandoSetas = true;
            } else {
                esquivaWASD = new EsquivaReflexo();
                esquivaWASD.iniciar();
                usandoSetas = false;
            }

            emEsquiva = true;
        } else {
            // Atualiza esquiva
            if (usandoSetas && esquivaSetas != null) {
                esquivaSetas.atualizar(delta);
                if (esquivaSetas.finalizado()) {
                    processarResultadoEsquiva(esquivaSetas.esquivouComSucesso());
                }
            } else if (esquivaWASD != null) {
                esquivaWASD.atualizar(delta);
                if (esquivaWASD.finalizado()) {
                    processarResultadoEsquiva(esquivaWASD.esquivouComSucesso());
                }
            }
        }
    }

    private void processarResultadoEsquiva(boolean esquivou) {
        if (esquivou) {
            batalha.mensagem = jogador.nome + " esquivou do ataque!";
        } else {
            batalha.ultimoAtaqueInimigo.aplicar(jogador);
            batalha.mensagem = inimigo.nome + " atacou com " + batalha.ultimoAtaqueInimigo.nome + "!";
        }
        emEsquiva = false;
        batalha.turnoDoJogador = true;
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
