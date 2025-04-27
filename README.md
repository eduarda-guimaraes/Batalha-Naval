# 🚢 Batalha Naval em Java 🚢

## 📖 Descrição
Projeto de implementação do clássico jogo **Batalha Naval** em **Java**.  
Permite partidas entre **dois jogadores** ou **jogador vs computador**, com alocação manual ou automática dos navios em um **tabuleiro 10x10**.

---

## 🎯 Funcionalidades
- 🔹 **Mapa** (matriz) de **10x10** para cada jogador.
- 🚢 **Alocação de Barcos**:
  - 1 navio de **4 espaços**
  - 2 navios de **3 espaços**
  - 3 navios de **2 espaços**
  - 4 navios de **1 espaço**
- 🧑‍🤝‍🧑 **Modo de jogo**: Jogador vs Jogador ou Jogador vs Computador.
- 🎲 **Alocação**:
  - Manual (escolha posição e direção)
  - Automática (sorteio de posições)
- ✅ **Validação** das posições (não sair do mapa e não sobrepor barcos).
- 🎯 **Sistema de ataque**:
  - Água 🌊: marca o acerto e passa a vez.
  - Barco 🚢: marca o acerto e continua jogando.
  - Ponto já atacado 🔄: deve repetir a jogada.
- 🤖 Computador atira aleatoriamente se for oponente.
- 🏆 O jogo termina quando todos os navios de um jogador forem afundados.
- 🔒 O mapa do oponente só mostra os tiros, sem revelar os navios.

---

## 🕹️ Como Jogar
1. Escolha o **modo de jogo**: Jogador vs Jogador 🧑‍🤝‍🧑 ou Jogador vs Computador 🤖.
2. Insira o **nome dos jogadores** 🧑👩.
3. Faça a **alocação dos navios**:
   - ✋ Manualmente: escolhendo linha, coluna e direção (horizontal ➡️ ou vertical ⬇️).
   - 🎲 Automaticamente: o jogo posiciona os navios.
4. 🏹 Ataque:
   - Se acertar água 🌊, passa a vez.
   - Se acertar um navio 🚢, continua jogando.
   - Se tentar atirar num lugar já atacado 🔄, repete a jogada.
5. 🏆 Vence quem destruir todos os navios do adversário!

---

## ⚓ Regras de Alocação
- 🚫 Barcos **não podem sair dos limites** do tabuleiro.
- 🚫 Barcos **não podem se sobrepor**.
- ↔️ Barcos podem ser posicionados **horizontalmente** ou **verticalmente**.
- 📏 Sempre validar antes de confirmar a posição!

---

## 🗺️ Representação no Mapa
- 🌊 **Água atingida:** (exemplo: `*`)
- 🎯 **Navio atingido:** (exemplo: `X`)
- ❓ **Água e navios intactos:** não são revelados ao adversário.

---

## ⚙️ Requisitos Técnicos
- 💻 **Linguagem:** Java
- 🛠️ **IDE recomendada:** IntelliJ IDEA, Eclipse ou VS Code com extensão Java
- ☕ **Versão Java:** 8 ou superior

---

## 🗂️ Estrutura Básica
- `Mapa.java` - Gerencia o tabuleiro e posições.
- `Navio.java` - Representa um navio (tamanho e posição).
- `Jogador.java` - Representa cada jogador.
- `Computador.java` - Controla a lógica do oponente automático.
- `BatalhaNaval.java` - Classe principal (fluxo do jogo).

---

## 📋 Exemplo de Mapa
```
   0 1 2 3 4 5 6 7 8 9
A  . . . . . . . . . .
B  . . . . . . . . . .
C  . . . B B B . . . .
D  . . . . . . . . . .
E  . . . . . . . . . .
F  . . . . . . . . . .
G  . . . . . . . . . .
H  . . . . . . . . . .
I  . . . . . . . . . .
J  . . . . . . . . . .
```
(Legenda: `.` = vazio, `B` = barco)

---

## 🚀 Melhorias Futuras
- 🖥️ Interface gráfica (GUI) com Java Swing ou JavaFX.
- 🧠 Inteligência artificial com diferentes níveis de dificuldade.
- 🏅 Sistema de ranking e estatísticas.

---

## ✨ Autor
Desenvolvido por Amanda e Eduarda 👨‍💻👩‍💻
