package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class EsquivaReflexoSetas {
    private String teclaAlvo; // "UP", "DOWN", etc.
    private boolean ativa;
    private float tempo;
    private float tempoParaCor;
    private boolean podeApertar;
    private boolean esquivou;
    private boolean falhou;
    private Random random = new Random();

    public void iniciar() {
        String[] teclas = {"UP", "DOWN", "LEFT", "RIGHT"};
        teclaAlvo = teclas[random.nextInt(teclas.length)];
        ativa = true;
        tempo = 0;
        tempoParaCor = 2f;
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
        font.setColor(podeApertar ? Color.GREEN : Color.WHITE);
        font.draw(batch, "Pressione SETA [" + teclaAlvo + "] na hora certa!", 50, 220);
        font.setColor(originalColor);
    }

    public boolean finalizado() {
        return !ativa;
    }

    public boolean esquivouComSucesso() {
        return esquivou;
    }

    private int getInputCode(String tecla) {
        switch (tecla) {
            case "UP": return Input.Keys.UP;
            case "DOWN": return Input.Keys.DOWN;
            case "LEFT": return Input.Keys.LEFT;
            case "RIGHT": return Input.Keys.RIGHT;
            default: return -1;
        }
    }
}
