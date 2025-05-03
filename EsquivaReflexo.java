package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class EsquivaReflexo {
    private char teclaAlvo;
    private boolean ativa;
    private float tempo;
    private float tempoParaCor;
    private boolean podeApertar;
    private boolean esquivou;
    private boolean falhou;

    private Random random = new Random();

    public EsquivaReflexo() {
        iniciar();
    }

    public void iniciar() {
        char[] teclas = {'W', 'A', 'S', 'D'};
        teclaAlvo = teclas[random.nextInt(teclas.length)];
        ativa = true;
        tempo = 0;
        tempoParaCor = 2f; // Tempo até o botão "piscar"
        podeApertar = false;
        esquivou = false;
        falhou = false;
    }

    public void atualizar(float delta) {
        if (!ativa) return;

        tempo += delta;

        if (tempo >= tempoParaCor && !podeApertar) {
            podeApertar = true;
        }

        if (podeApertar && tempo <= tempoParaCor + 1f) {
            if (Gdx.input.isKeyJustPressed(getInputCode(teclaAlvo))) {
                esquivou = true;
                ativa = false;
            }
        } else if (tempo > tempoParaCor + 1f && !esquivou) {
            falhou = true;
            ativa = false;
        }
    }

    public void desenhar(SpriteBatch batch, BitmapFont font) {
        if (!ativa) return;

        Color originalColor = font.getColor();

        if (podeApertar) {
            font.setColor(Color.GREEN);
        } else {
            font.setColor(Color.WHITE);
        }

        font.draw(batch, "Pressione [" + teclaAlvo + "] na hora certa!", 50, 250);
        font.setColor(originalColor);
    }

    public boolean finalizado() {
        return !ativa;
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
