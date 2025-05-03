package io.github.some_example_name;

public class Ataque {
    public String nome;
    public int dano;

    public Ataque(String nome, int dano) {
        this.nome = nome;
        this.dano = dano;
    }

    public void aplicar(Personagem alvo) {
        alvo.receberDano(dano);
    }
}
