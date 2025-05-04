package io.github.some_example_name;

import java.util.Random;

public class Batalha {
    public Personagem jogador;
    public Personagem inimigo;
    public boolean turnoDoJogador = true;
    public String mensagem = "Começo da batalha!";

    public Ataque ultimoAtaqueInimigo; // ← usado para adiar o dano até ver se o jogador esquivou

    private Random random = new Random();

    public Batalha(Personagem jogador, Personagem inimigo) {
        this.jogador = jogador;
        this.inimigo = inimigo;
    }

    public void turnoJogador(Ataque ataque) {
        ataque.aplicar(inimigo);
        mensagem = jogador.nome + " atacou com " + ataque.nome + "!";
        turnoDoJogador = false;
    }

    // Essa função apenas escolhe o ataque e armazena — não aplica o dano!
    public void prepararAtaqueInimigo() {
        if (random.nextBoolean()) {
            ultimoAtaqueInimigo = new Ataque("Investida Sombria", inimigo.ataque);
        } else {
            int danoBrutal = (int) (inimigo.ataque * 1.5f);
            ultimoAtaqueInimigo = new Ataque("Ataque Brutal", danoBrutal);
        }
    }

    // Chamado se o jogador não esquivar
    public void aplicarAtaqueInimigo() {
        if (ultimoAtaqueInimigo != null) {
            ultimoAtaqueInimigo.aplicar(jogador);
            mensagem = inimigo.nome + " atacou com " + ultimoAtaqueInimigo.nome + "!";
        }
        turnoDoJogador = true;
    }

    public boolean batalhaEncerrada() {
        return !jogador.estaVivo() || !inimigo.estaVivo();
    }

    public String resultado() {
        if (!jogador.estaVivo()) return "Você perdeu!";
        if (!inimigo.estaVivo()) return "Você venceu!";
        return "";
    }
}
