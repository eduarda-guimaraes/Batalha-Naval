package jogo;

import java.util.Scanner;
import java.util.Random;

public class BatalhaNaval {
    //Constantes
    private static final int AGUA = 0;
    private static final int NAVIO = 1;
    private static final int TIRO_AGUA = 2;
    private static final int TIRO_NAVIO = 3;

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        Random aleatorio = new Random();

        int[][] tabuleiroJogador1 = new int[10][10];
        int[][] tabuleiroJogador2 = new int[10][10];
        int[][] visaoJogador1 = new int[10][10];
        int[][] visaoJogador2 = new int[10][10];

        int[] tamanhosNavios = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        System.out.println("BATALHA NAVAL");
        mostrarLegenda();

        int modo = 0;
        while (modo != 1 && modo != 2) {
            System.out.println("\nEscolha o modo de jogo:");
            System.out.println("1 - Jogador vs Computador");
            System.out.println("2 - Jogador vs Jogador");
            System.out.print("Digite 1 ou 2: ");

            modo = ler.nextInt();
            ler.nextLine();

            if (modo != 1 && modo != 2) {
                System.out.println("Opção inválida! Digite 1 ou 2.");
            }
        }

        System.out.print("\nNome do Jogador 1: ");
        String jogador1 = ler.nextLine();
        String jogador2;
        switch (modo) {
            case 1:
                jogador2 = "Computador";
                break;
            case 2:
                jogador2 = lerNomeJogador2(ler);
                break;
            default:
                jogador2 = "";
        }

        posicionarNaviosJogador(ler, tabuleiroJogador1, tamanhosNavios, jogador1);

        if (modo == 1) {
            posicionarNaviosComputador(tabuleiroJogador2, tamanhosNavios);
            System.out.println("\nComputador posicionou seus navios.");
        } else {
            posicionarNaviosJogador(ler, tabuleiroJogador2, tamanhosNavios, jogador2);
        }

        boolean vezJogador1 = true;
        boolean jogoAcabou = false;

        while (!jogoAcabou) {
            if (vezJogador1) {
                jogoAcabou = turnoJogador(ler, tabuleiroJogador2, visaoJogador1, jogador1, jogador2);
                if (!jogoAcabou && !verificarAcerto(tabuleiroJogador2, visaoJogador1)) {
                    vezJogador1 = false;
                }
            } else {
                if (modo == 1) {
                    jogoAcabou = turnoComputador(tabuleiroJogador1, visaoJogador2, jogador2, jogador1);
                } else {
                    jogoAcabou = turnoJogador(ler, tabuleiroJogador1, visaoJogador2, jogador2, jogador1);
                }
                if (!jogoAcabou) {
                    vezJogador1 = true;
                }
            }
        }
    }

    private static String lerNomeJogador2(Scanner ler) {
        System.out.print("Nome do Jogador 2: ");
        return ler.nextLine();
    }

    public static void mostrarLegenda() {
        System.out.println("\nLEGENDA:");
        System.out.println("* - Água não explorada");
        System.out.println("O - Tiro na água (errou)");
        System.out.println("X - Tiro no navio (acertou)");
        System.out.println("N - Seu navio (apenas durante posicionamento)");
        System.out.println("----------------------");
    }

    public static void posicionarNaviosJogador(Scanner ler, int[][] tabuleiro, int[] tamanhosNavios, String jogador) {
        char resposta = ' ';
        while (resposta != 'S' && resposta != 'N') {
            System.out.println("\n" + jogador + ", deseja posicionar seus navios manualmente? (S/N)");
            resposta = ler.next().toUpperCase().charAt(0);
            ler.nextLine();
            if (resposta != 'S' && resposta != 'N') {
                System.out.println("Opção inválida! Digite S ou N.");
            }
        }

        if (resposta == 'S') {
            System.out.println("\n" + jogador + ", posicione seus navios:");
            for (int tamanho : tamanhosNavios) {
                boolean posicionado = false;
                while (!posicionado) {
                    System.out.println("\nNavio de tamanho " + tamanho + ":");
                    imprimirTabuleiro(tabuleiro, true);

                    int linha = lerLinhaValida(ler);
                    int coluna = lerColunaValida(ler);

                    char direcao = ' ';
                    while (direcao != 'H' && direcao != 'V') {
                        System.out.print("Direção (H-Horizontal, V-Vertical): ");
                        direcao = ler.next().toUpperCase().charAt(0);
                        ler.nextLine();
                        if (direcao != 'H' && direcao != 'V') {
                            System.out.println("Direção inválida! Digite H ou V.");
                        }
                    }

                    posicionado = verificarPosicao(tabuleiro, linha, coluna, tamanho, direcao == 'H' ? 1 : 2);

                    if (posicionado) {
                        posicionarNavio(tabuleiro, linha, coluna, tamanho, direcao == 'H' ? 1 : 2);
                        System.out.println("Navio posicionado com sucesso!");
                        imprimirTabuleiro(tabuleiro, true);
                    } else {
                        System.out.println("Posição inválida! Tente novamente.");
                    }
                }
            }
        } else {
            posicionarNaviosComputador(tabuleiro, tamanhosNavios);
            System.out.println("Navios posicionados automaticamente para " + jogador + ".");
        }
    }

    public static int lerLinhaValida(Scanner ler) {
        int linha = -1;
        while (linha < 0 || linha > 9) {
            System.out.print("Linha (0-9): ");
            String entrada = ler.nextLine();
            if (entrada.length() == 1 && entrada.charAt(0) >= '0' && entrada.charAt(0) <= '9') {
                linha = entrada.charAt(0) - '0';
            } else {
                System.out.println("Entrada inválida! Digite um número entre 0 e 9.");
            }
        }
        return linha;
    }

    public static int lerColunaValida(Scanner scanner) {
        int coluna = -1;
        while (coluna < 0 || coluna > 9) {
            System.out.print("Coluna (A-J): ");
            String entrada = scanner.nextLine().toUpperCase();
            if (entrada.length() == 1 && entrada.charAt(0) >= 'A' && entrada.charAt(0) <= 'J') {
                coluna = entrada.charAt(0) - 'A';
            } else {
                System.out.println("Coluna inválida! Digite uma letra entre A e J.");
            }
        }
        return coluna;
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
                    posicionarNavio(tabuleiro, linha, coluna, tamanho, direcao);
                }
            }
        }
    }

    public static boolean verificarPosicao(int[][] tabuleiro, int linha, int coluna, int tamanho, int direcao) {
        if (linha < 0 || linha >= 10 || coluna < 0 || coluna >= 10) return false;

        if (direcao == 1) {
            if (coluna + tamanho > 10) return false;
            for (int c = coluna; c < coluna + tamanho; c++) {
                if (tabuleiro[linha][c] != AGUA) return false;
            }
        } else {
            if (linha + tamanho > 10) return false;
            for (int l = linha; l < linha + tamanho; l++) {
                if (tabuleiro[l][coluna] != AGUA) return false;
            }
        }
        return true;
    }

    public static void posicionarNavio(int[][] tabuleiro, int linha, int coluna, int tamanho, int direcao) {
        if (direcao == 1) {
            for (int c = coluna; c < coluna + tamanho; c++) {
                tabuleiro[linha][c] = NAVIO;
            }
        } else {
            for (int l = linha; l < linha + tamanho; l++) {
                tabuleiro[l][coluna] = NAVIO;
            }
        }
    }

    public static void imprimirTabuleiro(int[][] tabuleiro, boolean mostrarNavios) {
        System.out.print("   ");
        for (char c = 'A'; c <= 'J'; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < 10; j++) {
                if (tabuleiro[i][j] == TIRO_NAVIO) {
                    System.out.print("X ");
                } else if (tabuleiro[i][j] == TIRO_AGUA) {
                    System.out.print("O ");
                } else if (tabuleiro[i][j] == NAVIO && mostrarNavios) {
                    System.out.print("N ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
    }

    public static boolean turnoJogador(Scanner ler, int[][] tabuleiroOponente, int[][] visao,
                                       String atacante, String oponente) {
        System.out.println("\nVez de " + atacante);
        System.out.println("Tabuleiro de " + oponente + ":");
        imprimirTabuleiro(visao, false);

        int linha = -1, coluna = -1;
        boolean posicaoValida = false;

        while (!posicaoValida) {
            linha = lerLinhaValida(ler);
            coluna = lerColunaValida(ler);

            if (visao[linha][coluna] == AGUA) {
                posicaoValida = true;
            } else {
                System.out.println("Você já atacou esta posição! Tente novamente.");
            }
        }

        boolean acertou = (tabuleiroOponente[linha][coluna] == NAVIO);

        if (acertou) {
            System.out.println("\nACERTOU!");
            visao[linha][coluna] = TIRO_NAVIO;
            tabuleiroOponente[linha][coluna] = TIRO_NAVIO;
        } else {
            System.out.println("\nÁGUA!");
            visao[linha][coluna] = TIRO_AGUA;
        }

        if (verificarVitoria(tabuleiroOponente)) {
            System.out.println("\nPARABÉNS " + atacante + "! VOCÊ AFUNDOU TODOS OS NAVIOS DE " + oponente + "!");
            return true;
        }

        return false;
    }

    public static boolean turnoComputador(int[][] tabuleiroJogador, int[][] visaoComputador, String computador, String jogador) {
        System.out.println("\nVez do" + computador);

        Random aleatorio = new Random();
        int linha, coluna;

        do {
            linha = aleatorio.nextInt(10);
            coluna = aleatorio.nextInt(10);
        } while (visaoComputador[linha][coluna] != AGUA);

        boolean acertou = (tabuleiroJogador[linha][coluna] == NAVIO);

        if (acertou) {
            System.out.println("O " + computador + " acertou seu navio na posição " + linha + "," + (char)(coluna + 'A'));
            visaoComputador[linha][coluna] = TIRO_NAVIO;
            tabuleiroJogador[linha][coluna] = TIRO_NAVIO;
        } else {
            System.out.println("O " + computador + " atirou na água em " + linha + "," + (char)(coluna + 'A'));
            visaoComputador[linha][coluna] = TIRO_AGUA;
        }

        if (verificarVitoria(tabuleiroJogador)) {
            System.out.println("\nO " + computador + " AFUNDOU TODOS OS SEUS NAVIOS!");
            return true;
        }

        return false;
    }

    public static boolean verificarAcerto(int[][] tabuleiro, int[][] visao) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabuleiro[i][j] == NAVIO && visao[i][j] == TIRO_NAVIO) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean verificarVitoria(int[][] tabuleiro) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabuleiro[i][j] == NAVIO) {
                    return false;
                }
            }
        }
        return true;
    }
}
