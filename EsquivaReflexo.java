package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class EsquivaReflexo {
    private enum Estado { TENSO, ESPERA_ESQUIVA, RESULTADO }

    private Estado estadoAtual;
    private char teclaExibida;
    private char teclaCorreta;
    private float tempo;
    private float tempoTotalTensao = 1.5f;
    private float tempoEsquiva = 0.7f;
    private float intervaloMudanca = 0.2f;
    private float tempoDesdeUltimaMudanca = 0f;

    private boolean esquivou = false;
    private boolean falhou = false;

    private Random random = new Random();
    private final char[] teclas = {'W', 'A', 'S', 'D'};

    public EsquivaReflexo() {
        iniciar();
    }

    public void iniciar() {
        estadoAtual = Estado.TENSO;
        tempo = 0;
        tempoDesdeUltimaMudanca = 0;
        teclaExibida = teclas[random.nextInt(teclas.length)];
    }

    public void atualizar(float delta) {
        tempo += delta;

        switch (estadoAtual) {
            case TENSO:
                tempoDesdeUltimaMudanca += delta;
                if (tempoDesdeUltimaMudanca >= intervaloMudanca) {
                    teclaExibida = teclas[random.nextInt(teclas.length)];
                    tempoDesdeUltimaMudanca = 0;
                }

                if (tempo >= tempoTotalTensao) {
                    estadoAtual = Estado.ESPERA_ESQUIVA;
                    tempo = 0;
                    teclaCorreta = teclas[random.nextInt(teclas.length)];
                    teclaExibida = teclaCorreta;
                }
                break;

            case ESPERA_ESQUIVA:
                if (Gdx.input.isKeyJustPressed(getInputCode(teclaCorreta))) {
                    esquivou = true;
                    estadoAtual = Estado.RESULTADO;
                } else if (tempo >= tempoEsquiva) {
                    falhou = true;
                    estadoAtual = Estado.RESULTADO;
                }
                break;

            case RESULTADO:
                // Nada a fazer, esperando o jogo ler resultado.
                break;
        }
    }

    public void desenhar(SpriteBatch batch, BitmapFont font) {
        if (estadoAtual == Estado.RESULTADO) return;

        Color originalColor = font.getColor();

        if (estadoAtual == Estado.ESPERA_ESQUIVA) {
            font.setColor(Color.GREEN);
        } else {
            font.setColor(Color.WHITE);
        }

        font.draw(batch, "Pressione [" + teclaExibida + "] na hora certa!", 50, 250);
        font.setColor(originalColor);
    }

    public boolean finalizado() {
        return estadoAtual == Estado.RESULTADO;
    }

    public boolean esquivouComSucesso() {
        return esquivou;
    }

    private int getInputCode(char tecla) {
        switch (tecla) {
            case 'W': return Input.Keys.W;
            case 'A': return Input.Keys.A;
            case 'S': return Input.Keys.S;
            case 'D': return Input.Keys.D;
            default: return -1;
        }
    }
}
