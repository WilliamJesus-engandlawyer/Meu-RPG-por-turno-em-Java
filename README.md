# Projeto BattleScreen - Jogo de Batalha em Java com libGDX

Este projeto é um trabalho do curso de Análise e Desenvolvimento de Sistemas da Estácio. Trata-se de um sistema batalha para um jogo rpg por turno (com foco em lançamento para android), desenvolvida em Java utilizando a biblioteca [libGDX](https://libgdx.com/).

## Descrição

O `BattleScreen` é a tela principal onde ocorre a batalha entre um personagem jogador e um inimigo. O sistema controla o turno de batalha, incluindo ações do jogador (ataque, magia, fuga) e ataques do inimigo, além de uma mecânica simples de esquiva.

### Funcionalidades principais:

- Turnos alternados entre jogador e inimigo.
- Interface gráfica com botões para ações (Ataque, Magia, Fugir).
- Mecânica de esquiva com animação.
- Indicadores de vida (HP) para ambos os personagens.
- Música de fundo em loop.
- Mensagens exibidas para feedback das ações.

## Tecnologias Utilizadas

- Java
- libGDX (framework para desenvolvimento de jogos)
- Scene2D UI para interface gráfica
- Assets gráficos e sonoros (imagens e música)
  
     
## 🕹️ Como Jogar

1. Execute o projeto.
2. No menu, use **Setas ↑ / ↓** para escolher entre "Começar" ou "Sair".
3. Durante a batalha:
   - Clique com o mouse para abrir o **menu de ações**.
   - Escolha entre **Ataque**, **Magia (não implementada)** ou **Fugir**.
   - Após o ataque do jogador, o **inimigo prepara um ataque**.
   - Use a tecla correta (seta para cima, baixo, esquerda ou direita) **no momento certo** para **esquivar** do ataque inimigo!
4. A batalha continua em turnos até que um dos personagens chegue a 0 de vida.

---

## 📁 Estrutura do Código

### 🎮 `Main.java`
Classe principal. Inicia o jogo e carrega a tela inicial (`FirstScreen`).

### 🧭 `FirstScreen.java`
Tela de **menu principal** com duas opções: `Começar` e `Sair`. Usa entradas de teclado para navegação.

### ⚔️ `BattleScreen.java`
Tela da **batalha principal**. Lida com:
- Interface de combate (botões);
- Turnos;
- Execução de ataques e esquiva;
- Exibição de mensagens e HUD.

### 🔁 `Batalha.java`
Classe que implementa a **lógica da batalha**:
- Alternância de turnos;
- Armazenamento de ataques;
- Aplicação de dano;
- Detecção de vitória ou derrota.

### 🕹️ `EsquivaReflexoSetas.java`
Minigame de **esquiva baseada em reflexos**:
- Mostra uma dica com o nome de uma seta (criptografado como: "melhor abaixar", "será que é direita?", etc.).
- Após um curto tempo, o jogador deve apertar a tecla correta **dentro da janela de tempo**.

### 👤 `Personagem.java`
Classe que representa um personagem da batalha, com:
- Nome
- HP
- Ataque
- Função `estaVivo()` para verificar status

### 💥 `Ataque.java`
Define os ataques, com:
- Nome do ataque
- Dano causado
- Método `aplicar(Personagem alvo)`

---

## 📦 Recursos (Assets)

Os seguintes arquivos devem estar na pasta `assets/` do seu projeto libGDX:

- `ui/uiskin.json` e pastas associadas (interface dos botões)
- Imagens:
  - `TESTE.png`, `TESTE2.png` — Personagens
  - `inimigoteste.png` — Inimigo
  - `FUNDO.png` — Fundo da batalha
- Áudio:
  - `batalha.mp3` — Música de fundo da batalha

---

## 🔧 Como Executar o Projeto

1. Instale o **JDK 8+** e configure o ambiente com o **libGDX** (pode usar o [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff)).
2. Importe os arquivos `.java` no seu projeto.
3. Coloque os recursos na pasta `assets/`.
4. Execute a aplicação a partir da classe `Main.java`.

---

## 🧩 Funcionalidades Implementadas

✅ Menu inicial com navegação  
✅ Batalha por turnos  
✅ Ataque com dano  
✅ Sistema de esquiva baseado em reflexos  
✅ Interface gráfica básica com botão e mensagem  
✅ Música de fundo em loop  
❌ Magia ainda não implementada  
✅ Sistema de fuga (encerra batalha)  
✅ Fim da batalha com mensagem de vitória ou derrota

---

## 👤 Autor

**William**  
Estudante de Análise e Desenvolvimento de Sistemas — Estácio  
Apaixonado por tecnologia, jogos e projetos criativos em Java.

---

## 📚 Licença

Projeto acadêmico com fins de aprendizado.  
Você pode usar, estudar, modificar e compartilhar livremente. 🚀
