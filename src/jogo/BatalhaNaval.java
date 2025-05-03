package jogo;

import java.util.Scanner;
import java.util.Random;

public class BatalhaNaval {
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        Random aleatorio = new Random();

        // Cria os tabuleiros para os jogadores (10x10)
        int[][] tabuleiroJogador1 = new int[10][10];
        int[][] tabuleiroJogador2 = new int[10][10];
        int[][] visaoJogador1 = new int[10][10];
        int[][] visaoJogador2 = new int[10][10];

        // Tamanhos dos navios
        int[] tamanhosNavios = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        System.out.println("BATALHA NAVAL");
        mostrarLegenda();

        // Configuração do modo de jogo
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Jogador vs Computador");
        System.out.println("2 - Jogador vs Jogador");
        System.out.print("Digite 1 ou 2: ");
        int modo = ler.nextInt();
        ler.nextLine();

        // Nomes dos jogadores
        System.out.print("Nome do Jogador 1: ");
        String jogador1 = ler.nextLine();
        String jogador2;

        if (modo == 1) {
            jogador2 = "Computador";
        } else {
            System.out.print("Nome do Jogador 2: ");
            jogador2 = ler.nextLine();
        }

        // Posicionamento dos navios
        System.out.println("\n" + jogador1 + ", posicione seus navios:");
        posicionarNavios(ler, tabuleiroJogador1, tamanhosNavios);

        if (modo == 1) {
            posicionarNaviosComputador(tabuleiroJogador2, tamanhosNavios);
            System.out.println("\nComputador posicionou seus navios.");
        } else {
            System.out.println("\n" + jogador2 + ", posicione seus navios:");
            posicionarNavios(ler, tabuleiroJogador2, tamanhosNavios);
        }

        // Jogo principal
        boolean vezJogador1 = true;
        boolean jogoAcabou = false;

        while (!jogoAcabou) {
            if (vezJogador1) {
                System.out.println("Tabuleiro do oponente:");
                imprimirTabuleiro(visaoJogador2, false);

                boolean acertou = realizarAtaque(ler, tabuleiroJogador2, visaoJogador2);

                if (todosNaviosAfundados(tabuleiroJogador2)) {
                    System.out.println("\nPARABÉNS " + jogador1 + "! VOCÊ VENCEU!");
                    jogoAcabou = true;
                } else if (!acertou) {
                    vezJogador1 = false;
                }
            } else {
                System.out.println("\nVez de " + jogador2);
                System.out.println("Tabuleiro do oponente:");
                imprimirTabuleiro(visaoJogador1, false);

                boolean acertou;
                if (modo == 1) {
                    acertou = realizarAtaqueComputador(tabuleiroJogador1, visaoJogador1);
                } else {
                    acertou = realizarAtaque(ler, tabuleiroJogador1, visaoJogador1);
                }

                if (todosNaviosAfundados(tabuleiroJogador1)) {
                    System.out.println("\nPARABÉNS " + jogador2 + "! VOCÊ VENCEU!");
                    jogoAcabou = true;
                } else if (!acertou) {
                    vezJogador1 = true;
                }
            }
        }
    }

    public static void mostrarLegenda() {
        System.out.println("\nLEGENDA:");
        System.out.println("~ - Água");
        System.out.println("N - Navio (apenas durante o posicionamento)");
        System.out.println("O - Tiro na água");
        System.out.println("X - Tiro no navio (acerto)");
        System.out.println("----------------------");
    }

    public static void posicionarNavios(Scanner ler, int[][] tabuleiro, int[] tamanhosNavios) {
        for (int tamanho : tamanhosNavios) {
            imprimirTabuleiro(tabuleiro, true);
            System.out.println("Posicionando navio de tamanho " + tamanho);

            boolean posicionado = false;
            while (!posicionado) {
                System.out.print("Digite a linha (0-9): ");
                int linha = ler.nextInt();
                System.out.print("Digite a coluna (0-9): ");
                int coluna = ler.nextInt();
                System.out.print("Direção (1-Horizontal, 2-Vertical): ");
                int direcao = ler.nextInt();
                ler.nextLine();

                posicionado = verificarPosicao(tabuleiro, linha, coluna, tamanho, direcao);

                if (posicionado) {
                    if (direcao == 1) {
                        for (int c = coluna; c < coluna + tamanho; c++) {
                            tabuleiro[linha][c] = 1;
                        }
                    } else {
                        for (int l = linha; l < linha + tamanho; l++) {
                            tabuleiro[l][coluna] = 1;
                        }
                    }
                    System.out.println("Navio posicionado com sucesso!");
                } else {
                    System.out.println("Posição inválida! Tente novamente.");
                }
            }
        }
    }

    public static void posicionarNaviosComputador(int[][] tabuleiro, int[] tamanhosNavios) {
        Random aleatorio = new Random();

        for (int tamanho : tamanhosNavios) {
            boolean posicionado = false;

            while (!posicionado) {
                int linha = aleatorio.nextInt(10);
                int coluna = aleatorio.nextInt(10);
                int direcao = aleatorio.nextInt(2) + 1;

                posicionado = verificarPosicao(tabuleiro, linha, coluna, tamanho, direcao);

                if (posicionado) {
                    if (direcao == 1) {
                        for (int c = coluna; c < coluna + tamanho; c++) {
                            tabuleiro[linha][c] = 1;
                        }
                    } else {
                        for (int l = linha; l < linha + tamanho; l++) {
                            tabuleiro[l][coluna] = 1;
                        }
                    }
                }
            }
        }
    }

    public static boolean verificarPosicao(int[][] tabuleiro, int linha, int coluna, int tamanho, int direcao) {
        if (linha < 0 || linha > 9 || coluna < 0 || coluna > 9) return false;

        if (direcao == 1) {
            if (coluna + tamanho > 10) return false;
            for (int c = coluna; c < coluna + tamanho; c++) {
                if (tabuleiro[linha][c] != 0) return false;
            }
        } else {
            if (linha + tamanho > 10) return false;
            for (int l = linha; l < linha + tamanho; l++) {
                if (tabuleiro[l][coluna] != 0) return false;
            }
        }
        return true;
    }

    public static boolean realizarAtaque(Scanner ler, int[][] tabuleiro, int[][] visao) {
        int linha, coluna;

        while (true) {
            System.out.print("Digite a linha para atacar (0-9): ");
            linha = ler.nextInt();
            System.out.print("Digite a coluna para atacar (0-9): ");
            coluna = ler.nextInt();
            ler.nextLine();

            if (linha < 0 || linha > 9 || coluna < 0 || coluna > 9) {
                System.out.println("Posição inválida!");
                continue;
            }

            if (visao[linha][coluna] == 2 || visao[linha][coluna] == 3) {
                System.out.println("Você já atirou aqui! Escolha outra posição.");
            } else {
                break;
            }
        }

        if (tabuleiro[linha][coluna] == 1) {
            System.out.println("ACERTOU UM NAVIO!");
            tabuleiro[linha][coluna] = 3;
            visao[linha][coluna] = 3;
            return true;
        } else {
            System.out.println("ÁGUA!");
            tabuleiro[linha][coluna] = 2;
            visao[linha][coluna] = 2;
            return false;
        }
    }

    public static boolean realizarAtaqueComputador(int[][] tabuleiro, int[][] visao) {
        Random aleatorio = new Random();
        int linha, coluna;

        do {
            linha = aleatorio.nextInt(10);
            coluna = aleatorio.nextInt(10);
        } while (visao[linha][coluna] == 2 || visao[linha][coluna] == 3);

        System.out.println("Computador atacou na posição " + linha + "," + coluna);

        if (tabuleiro[linha][coluna] == 1) {
            System.out.println("O computador ACERTOU um navio!");
            tabuleiro[linha][coluna] = 3;
            visao[linha][coluna] = 3;
            return true;
        } else {
            System.out.println("O computador atirou na ÁGUA!");
            tabuleiro[linha][coluna] = 2;
            visao[linha][coluna] = 2;
            return false;
        }
    }

    public static boolean todosNaviosAfundados(int[][] tabuleiro) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabuleiro[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void imprimirTabuleiro(int[][] tabuleiro, boolean mostrarNavios) {
        System.out.print("   ");
        for (int j = 0; j < 10; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < 10; j++) {
                char simbolo;
                if (mostrarNavios) {
                    simbolo = (tabuleiro[i][j] == 1) ? 'N' : '~';
                } else {
                    if (tabuleiro[i][j] == 2) {
                        simbolo = 'O';
                    } else if (tabuleiro[i][j] == 3) {
                        simbolo = 'X';
                    } else {
                        simbolo = '~';
                    }
                }
                System.out.print(simbolo + " ");
            }
            System.out.println();
        }
    }
}
