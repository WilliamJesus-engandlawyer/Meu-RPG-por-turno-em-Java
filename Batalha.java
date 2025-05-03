package io.github.some_example_name;

public class Batalha {
    public Personagem jogador;
    public Personagem inimigo;
    public boolean turnoDoJogador = true;
    public String mensagem = "Começo da batalha!";

    public Batalha(Personagem jogador, Personagem inimigo) {
        this.jogador = jogador;
        this.inimigo = inimigo;
    }

    public void turnoJogador(Ataque ataque) {
        ataque.aplicar(inimigo);
        mensagem = jogador.nome + " atacou com " + ataque.nome + "!";
        turnoDoJogador = false;
    }

    public void turnoInimigo() {
        Ataque ataque = new Ataque("Investida Sombria", inimigo.ataque);
        ataque.aplicar(jogador);
        mensagem = inimigo.nome + " atacou com " + ataque.nome + "!";
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
