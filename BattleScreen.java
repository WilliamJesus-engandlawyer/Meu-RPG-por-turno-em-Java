package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.audio.Music;

public class BattleScreen implements Screen {
    private enum EstadoDeTurno {
        AGUARDANDO_ACAO_JOGADOR,
        INICIANDO_ATAQUE_INIMIGO,
        ESQUIVANDO,
        ANIMANDO_ESQUIVA,
        FIM_DE_JOGO
    }

    private EstadoDeTurno estado = EstadoDeTurno.AGUARDANDO_ACAO_JOGADOR;

    private Stage stage;
    private Skin skin;
    private boolean mostrarMenu = false;
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Personagem jogador;
    private Personagem inimigo;
    private Batalha batalha;
    private Ataque ataqueBasico;
    private Music musicaDeFundo;

    private EsquivaReflexo esquiva;
    private boolean emEsquiva = false;

    private Texture personagemTexture;
    private Texture personagemEsquivandoTexture;
    private Texture inimigoTexture;
    private Sprite inimigoSprite;
    private Texture fundoBatalha;


    private boolean mostrarAnimacaoEsquiva = false;
    private float tempoAnimacaoEsquiva = 0f;

    public BattleScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        jogador = new Personagem("Ana", 100, 15);
        inimigo = new Personagem("Alien", 140, 10);
        batalha = new Batalha(jogador, inimigo);
        ataqueBasico = new Ataque("Espadada", jogador.ataque);

        musicaDeFundo = Gdx.audio.newMusic(Gdx.files.internal("batalha.mp3"));
        musicaDeFundo.setLooping(true); // para a música ficar em loop
        musicaDeFundo.play();


        personagemTexture = new Texture("TESTE.png");
        personagemEsquivandoTexture = new Texture("TESTE2.png");
        inimigoTexture = new Texture("inimigoteste.png");
        inimigoSprite = new Sprite(inimigoTexture);
        inimigoSprite.flip(true, false); // Inverte horizontalmente (espelha)
        fundoBatalha = new Texture("FUNDO.png");
        montarMenu();
    }

    private void montarMenu() {
        stage.clear(); // limpa botões antigos
        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.center();

        TextButton ataqueButton = new TextButton("Ataque", skin);
        TextButton magiaButton = new TextButton("Magia", skin);
        TextButton fugirButton = new TextButton("Fugir", skin);

        ataqueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (estado == EstadoDeTurno.AGUARDANDO_ACAO_JOGADOR) {
                    batalha.turnoJogador(ataqueBasico);
                    batalha.prepararAtaqueInimigo();
                    estado = EstadoDeTurno.INICIANDO_ATAQUE_INIMIGO;
                    mostrarMenu = false;
                    stage.clear();
                }
            }
        });

        magiaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (estado == EstadoDeTurno.AGUARDANDO_ACAO_JOGADOR) {
                    batalha.mensagem = "Magia ainda não implementada!";
                    mostrarMenu = false;
                    stage.clear();
                }
            }
        });

        fugirButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (estado == EstadoDeTurno.AGUARDANDO_ACAO_JOGADOR) {
                    batalha.mensagem = "Você fugiu!";
                    batalha.jogador.hp = 0;
                    estado = EstadoDeTurno.FIM_DE_JOGO;
                    mostrarMenu = false;
                    stage.clear();
                }
            }
        });

        menuTable.add(ataqueButton).pad(10);
        menuTable.row();
        menuTable.add(magiaButton).pad(10);
        menuTable.row();
        menuTable.add(fugirButton).pad(10);

        stage.addActor(menuTable);
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);

        batch.begin();
        batch.draw(fundoBatalha, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

         // Inimigo desenhado à direita
        inimigoSprite.setPosition(Gdx.graphics.getWidth() - 200, 150);
        inimigoSprite.setSize(128, 128);
        inimigoSprite.draw(batch);


        Texture texturaAtual = (mostrarAnimacaoEsquiva) ? personagemEsquivandoTexture : personagemTexture;

        if (texturaAtual == personagemEsquivandoTexture) {
            batch.draw(texturaAtual, 50, 150, 80, 109);
        } else {
            batch.draw(texturaAtual, 50, 150, 128, 128);
        }

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

        // CORRETO: esse trecho deve estar aqui
        if (mostrarMenu) {
            stage.act(delta);
            stage.draw();
        }
    }


    private void update(float delta) {
        if (batalha.batalhaEncerrada()) {
            estado = EstadoDeTurno.FIM_DE_JOGO;
            return;
        }

        switch (estado) {
            case AGUARDANDO_ACAO_JOGADOR:
                if (!mostrarMenu && Gdx.input.justTouched()) {
                    mostrarMenu = true;
                    montarMenu();
                }
                break;

            case INICIANDO_ATAQUE_INIMIGO:
                esquiva = new EsquivaReflexo();
                emEsquiva = true;
                estado = EstadoDeTurno.ESQUIVANDO;
                break;

            case ESQUIVANDO:
                if (esquiva != null) {
                    esquiva.atualizar(delta);
                    if (esquiva.finalizado()) {
                        if (esquiva.esquivouComSucesso()) {
                            mostrarAnimacaoEsquiva = true;
                            tempoAnimacaoEsquiva = 0.2f;
                            batalha.mensagem = jogador.nome + " esquivou do ataque!";
                            estado = EstadoDeTurno.ANIMANDO_ESQUIVA;
                        } else {
                            batalha.aplicarAtaqueInimigo();
                            estado = EstadoDeTurno.AGUARDANDO_ACAO_JOGADOR;
                            mostrarMenu = true;
                            montarMenu();
                        }
                        emEsquiva = false;
                    }
                }
                break;

            case ANIMANDO_ESQUIVA:
                tempoAnimacaoEsquiva -= delta;
                if (tempoAnimacaoEsquiva <= 0f) {
                    mostrarAnimacaoEsquiva = false;
                    estado = EstadoDeTurno.AGUARDANDO_ACAO_JOGADOR;
                    mostrarMenu = true;
                    montarMenu();
                }
                break;

            case FIM_DE_JOGO:
                // Nada a fazer
                break;
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        personagemTexture.dispose();
        personagemEsquivandoTexture.dispose();
        inimigoTexture.dispose();
        musicaDeFundo.dispose();
        stage.dispose();
        skin.dispose();
    }
}
