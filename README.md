# 🚢 Batalha Naval em Java

## 📖 Descrição
Projeto de implementação do clássico jogo **Batalha Naval**, desenvolvido em **Java**.  
O jogo permite partidas entre **dois jogadores** ou **jogador contra computador**, com opção de alocação automática ou manual dos navios em um tabuleiro 10x10.

---

## ⚙️ Funcionalidades
- 🗺️ Tabuleiro de 10x10 para cada jogador.
- 🚤 Alocação dos navios:
  - 1 navio de 4 espaços
  - 2 navios de 3 espaços
  - 3 navios de 2 espaços
  - 4 navios de 1 espaço
- 🆚 Escolha entre modo Jogador vs Jogador ou Jogador vs Computador.
- 🛠️ Alocação manual (posição e direção) ou automática (aleatória).
- ✅ Validação de posição para impedir sobreposição e extrapolação do tabuleiro.
- 🎯 Sistema de ataque:
  - Acerto na água: marca o mapa e passa a vez.
  - Acerto em navio: marca o mapa e o jogador continua jogando.
  - Ataque em posição já atingida: o jogador precisa jogar novamente.
- 🤖 Computador realiza ataques aleatórios.
- 🏆 O jogo termina quando todos os navios de um jogador forem destruídos.

---

## 🎮 Como Jogar
1. Escolha o modo de jogo (contra outro jogador ou contra o computador).
2. Informe o nome dos jogadores.
3. Escolha a forma de alocação dos navios:
   - Manual: definindo posição e direção (horizontal ➡️ ou vertical ⬇️).
   - Automática: o sistema aloca os navios de forma aleatória.
4. Durante o turno:
   - Escolha uma linha e coluna para atacar o oponente.
   - O sistema informa se o ataque foi na água ou em um navio.
5. O primeiro jogador a destruir todos os navios do adversário vence.

---

## 🗺️ Representação do Mapa
- `X` : Barco atingido
- `O` : Água atingida
- `*` : Área ainda não atingida e sem informações reveladas

---

## 🖼️ Prints de Tela

### Tela inicial de escolha de modo de jogo
```
Bem-vindo à Batalha Naval!
Escolha o modo de jogo:
1 - Jogador vs Jogador
2 - Jogador vs Computador
```

### Exemplo de Mapa Durante o Jogo
```
  0 1 2 3 4 5 6 7 8 9
A * * * * * * * * * *
B * * * O * * * * * *
C * * X X * * * * * *
D * * * * * * * * * *
E * * * * * * * * * *
F * * * * * * * * * *
G * * * * * * * * * *
H * * * * * * * * * *
I * * * * * * * * * *
J * * * * * * * * * *
```

- `X`: Barco atingido  
- `O`: Água atingida  
- `*`: Área ainda não atingida e sem informações reveladas

### Tela de vitória
```
Parabéns, [Nome do Vencedor]! Você afundou todos os navios do oponente! 🏆
```

---

## 📋 Requisitos
- ☕ Java 8 ou superior
- 💻 IDE para desenvolvimento Java (como IntelliJ IDEA, Eclipse ou VS Code)

---

## 👥 Desenvolvedores
- [Amanda de Mello Ferreira]
- [Eduarda Guimarães]
