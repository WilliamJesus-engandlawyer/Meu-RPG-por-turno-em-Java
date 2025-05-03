package io.github.some_example_name;

public class Personagem {
    public String nome;
    public int hp;
    public int ataque;

    public Personagem(String nome, int hp, int ataque) {
        this.nome = nome;
        this.hp = hp;
        this.ataque = ataque;
    }

    public void receberDano(int dano) {
        hp -= dano;
        if (hp < 0) hp = 0;
    }

    public boolean estaVivo() {
        return hp > 0;
    }
}
