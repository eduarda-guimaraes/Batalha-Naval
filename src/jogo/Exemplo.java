package jogo;

import java.util.*;

public class Exemplo {
    static final int TAMANHO = 10;
    static final char AGUA = '~';
    static final char NAVIO = 'B';
    static final char TIRO_NA_AGUA = 'O';
    static final char TIRO_NO_NAVIO = 'X';

    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();

    static char[][] criarMapaVazio() {
        char[][] mapa = new char[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            Arrays.fill(mapa[i], AGUA);
        }
        return mapa;
    }

    static void mostrarMapa(char[][] mapa, boolean esconderBarcos) {
        System.out.print("   ");
        for (char coluna = 'A'; coluna < 'A' + TAMANHO; coluna++) {
            System.out.printf("%-2s", coluna);
        }
        System.out.println();
        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%-2d ", i);
            for (int j = 0; j < TAMANHO; j++) {
                char celula = mapa[i][j];
                if (esconderBarcos && celula == NAVIO) {
                    System.out.print(AGUA + " ");
                } else {
                    System.out.print(celula + " ");
                }
            }
            System.out.println();
        }
    }

    static boolean podeColocarBarco(char[][] mapa, int linha, int coluna, int tamanho, boolean horizontal) {
        if (horizontal) {
            if (coluna + tamanho > TAMANHO) return false;
            for (int i = 0; i < tamanho; i++) {
                if (mapa[linha][coluna + i] != AGUA) return false;
            }
        } else {
            if (linha + tamanho > TAMANHO) return false;
            for (int i = 0; i < tamanho; i++) {
                if (mapa[linha + i][coluna] != AGUA) return false;
            }
        }
        return true;
    }

    static void colocarBarco(char[][] mapa, int linha, int coluna, int tamanho, boolean horizontal) {
        for (int i = 0; i < tamanho; i++) {
            if (horizontal) {
                mapa[linha][coluna + i] = NAVIO;
            } else {
                mapa[linha + i][coluna] = NAVIO;
            }
        }
    }

    static void alocarBarcosAutomaticamente(char[][] mapa, String nomeJogador) {
        int[] barcos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for (int tamanho : barcos) {
            boolean colocado = false;
            while (!colocado) {
                int linha = rand.nextInt(TAMANHO);
                int coluna = rand.nextInt(TAMANHO);
                boolean horizontal = rand.nextBoolean();
                if (podeColocarBarco(mapa, linha, coluna, tamanho, horizontal)) {
                    colocarBarco(mapa, linha, coluna, tamanho, horizontal);
                    colocado = true;
                }
            }
        }
        System.out.println(nomeJogador + " alocou os navios automaticamente.");
    }

    static void alocarBarcosManual(char[][] mapa, String nomeJogador) {
        int[] barcos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for (int tamanho : barcos) {
            boolean colocado = false;
            while (!colocado) {
                System.out.println(nomeJogador + ", posicione o barco de tamanho " + tamanho);
                mostrarMapa(mapa, false);
                System.out.print("Linha (0-9): ");
                int linha = scanner.nextInt();
                System.out.print("Coluna (A-J): ");
                char colunaChar = scanner.next().toUpperCase().charAt(0);
                int coluna = colunaChar - 'A';
                System.out.print("Horizontal (true/false)? ");
                boolean horizontal = scanner.nextBoolean();

                if (linha >= 0 && linha < TAMANHO && coluna >= 0 && coluna < TAMANHO &&
                        podeColocarBarco(mapa, linha, coluna, tamanho, horizontal)) {
                    colocarBarco(mapa, linha, coluna, tamanho, horizontal);
                    colocado = true;
                } else {
                    System.out.println("Posição inválida, tente novamente.");
                }
            }
        }
    }

    static boolean realizarAtaque(char[][] mapa, int linha, int coluna) {
        if (mapa[linha][coluna] == NAVIO) {
            mapa[linha][coluna] = TIRO_NO_NAVIO;
            return true;
        } else if (mapa[linha][coluna] == AGUA) {
            mapa[linha][coluna] = TIRO_NA_AGUA;
            return false;
        }
        return false; // já atirado
    }

    static boolean todosNaviosAfundados(char[][] mapa) {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (mapa[i][j] == NAVIO) return false;
            }
        }
        return true;
    }

    static void turnoJogador(String nome, char[][] mapaAlvo, boolean esconderBarcos) {
        while (true) {
            mostrarMapa(mapaAlvo, esconderBarcos);
            System.out.println(nome + ", escolha uma posição para atacar.");
            System.out.print("Linha (0-9): ");
            int linha = scanner.nextInt();
            System.out.print("Coluna (A-J): ");
            char colunaChar = scanner.next().toUpperCase().charAt(0);
            int coluna = colunaChar - 'A';

            if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
                System.out.println("Coordenadas inválidas.");
                continue;
            }

            char alvo = mapaAlvo[linha][coluna];
            if (alvo == TIRO_NO_NAVIO || alvo == TIRO_NA_AGUA) {
                System.out.println("Você já atirou aí. Tente outra posição.");
                continue;
            }

            boolean acerto = realizarAtaque(mapaAlvo, linha, coluna);
            if (acerto) {
                System.out.println("Acertou! Jogue novamente.");
                if (todosNaviosAfundados(mapaAlvo)) break;
            } else {
                System.out.println("Errou. Fim do turno.");
                break;
            }
        }
    }

    static void turnoComputador(char[][] mapaAlvo) {
        while (true) {
            int linha = rand.nextInt(TAMANHO);
            int coluna = rand.nextInt(TAMANHO);

            char alvo = mapaAlvo[linha][coluna];
            if (alvo == TIRO_NO_NAVIO || alvo == TIRO_NA_AGUA) continue;

            boolean acerto = realizarAtaque(mapaAlvo, linha, coluna);
            System.out.printf("Computador atirou em %d%c e %s.\n", linha, (char)('A' + coluna), acerto ? "acertou" : "errou");

            if (!acerto || todosNaviosAfundados(mapaAlvo)) break;
        }
    }

    public static void main(String[] args) {
        System.out.println("==== BATALHA NAVAL ====");
        System.out.print("Digite o nome do Jogador 1: ");
        String jogador1 = scanner.nextLine();

        System.out.print("Deseja jogar contra outro jogador ou contra o computador? (2/jogadores ou 1/pc): ");
        int modo = scanner.nextInt();
        scanner.nextLine(); // Limpa buffer

        String jogador2 = (modo == 2) ? scanner.nextLine() : "Computador";

        char[][] mapa1 = criarMapaVazio();
        char[][] mapa2 = criarMapaVazio();

        System.out.print(jogador1 + ", deseja alocar os barcos manualmente? (s/n): ");
        boolean manual1 = scanner.next().equalsIgnoreCase("s");
        if (manual1) alocarBarcosManual(mapa1, jogador1);
        else alocarBarcosAutomaticamente(mapa1, jogador1);

        if (modo == 2) {
            System.out.print(jogador2 + ", deseja alocar os barcos manualmente? (s/n): ");
            boolean manual2 = scanner.next().equalsIgnoreCase("s");
            if (manual2) alocarBarcosManual(mapa2, jogador2);
            else alocarBarcosAutomaticamente(mapa2, jogador2);
        } else {
            alocarBarcosAutomaticamente(mapa2, jogador2);
        }

        // Loop do jogo
        while (true) {
            System.out.println("\n--- Turno de " + jogador1 + " ---");
            turnoJogador(jogador1, mapa2, modo == 1);
            if (todosNaviosAfundados(mapa2)) {
                System.out.println(jogador1 + " venceu!");
                break;
            }

            System.out.println("\n--- Turno de " + jogador2 + " ---");
            if (modo == 2) {
                turnoJogador(jogador2, mapa1, false);
            } else {
                turnoComputador(mapa1);
            }

            if (todosNaviosAfundados(mapa1)) {
                System.out.println(jogador2 + " venceu!");
                break;
            }
        }

        scanner.close();
    }
}