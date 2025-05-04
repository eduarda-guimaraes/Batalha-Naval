package jogo;

import java.util.Scanner;
import java.util.Random;

public class BatalhaNaval {
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

        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Jogador vs Computador");
        System.out.println("2 - Jogador vs Jogador");
        System.out.print("Digite 1 ou 2: ");
        int modo = ler.nextInt();
        ler.nextLine();

        System.out.print("Nome do Jogador 1: ");
        String jogador1 = ler.nextLine();
        String jogador2;

        if (modo == 1) {
            jogador2 = "Computador";
        } else {
            System.out.print("Nome do Jogador 2: ");
            jogador2 = ler.nextLine();
        }

        System.out.println("\n" + jogador1 + ", deseja posicionar seus navios manualmente?");
        System.out.print("Digite S para sim ou N para não (posicionamento automático): ");
        char resposta1 = ler.next().toUpperCase().charAt(0);
        ler.nextLine();

        if (resposta1 == 'S') {
            System.out.println(jogador1 + ", posicione seus navios:");
            posicionarNavios(ler, tabuleiroJogador1, tamanhosNavios);
        } else {
            posicionarNaviosComputador(tabuleiroJogador1, tamanhosNavios);
            System.out.println("Navios posicionados automaticamente para " + jogador1 + ".");
        }

        if (modo == 1) {
            posicionarNaviosComputador(tabuleiroJogador2, tamanhosNavios);
            System.out.println("\nComputador posicionou seus navios.");
        } else {
            System.out.println("\n" + jogador2 + ", deseja posicionar seus navios manualmente?");
            System.out.print("Digite S para sim ou N para não (posicionamento automático): ");
            char resposta2 = ler.next().toUpperCase().charAt(0);
            ler.nextLine();

            if (resposta2 == 'S') {
                System.out.println(jogador2 + ", posicione seus navios:");
                posicionarNavios(ler, tabuleiroJogador2, tamanhosNavios);
            } else {
                posicionarNaviosComputador(tabuleiroJogador2, tamanhosNavios);
                System.out.println("Navios posicionados automaticamente para " + jogador2 + ".");
            }
        }

        boolean vezJogador1 = true;
        boolean jogoAcabou = false;

        while (!jogoAcabou) {
            if (vezJogador1) {
                System.out.println("\nTabuleiro do oponente:");
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
        System.out.println("* - Água");
        System.out.println("N - Navio (durante o posicionamento)");
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
                System.out.print("Linha (0-9): ");
                int linha = ler.nextInt();
                System.out.print("Coluna (A-J): ");
                char colLetra = ler.next().toUpperCase().charAt(0);
                int coluna = colLetra - 'A';

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
            System.out.print("Linha (0-9): ");
            linha = ler.nextInt();
            System.out.print("Coluna (A-J): ");
            char colLetra = ler.next().toUpperCase().charAt(0);
            coluna = colLetra - 'A';
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
            tabuleiro[linha][coluna] = 3;  // Marca o acerto
            visao[linha][coluna] = 3;      // Atualiza a visão do jogador

            // Mostrar o navio completo após o acerto
            mostrarNavioCompleto(tabuleiro, linha, coluna);

            return true;
        } else {
            System.out.println("ÁGUA!");
            tabuleiro[linha][coluna] = 2;  // Marca como água
            visao[linha][coluna] = 2;      // Atualiza a visão do jogador
            return false;
        }
    }

    // Método para mostrar o navio completo
    public static void mostrarNavioCompleto(int[][] tabuleiro, int linha, int coluna) {
        // Vamos mostrar o navio completo em qualquer direção após o acerto
        // Verificar horizontal
        for (int i = 0; i < 10; i++) {
            if (tabuleiro[linha][i] == 1) {
                tabuleiro[linha][i] = 3;  // Marca a parte do navio
            }
        }

        // Verificar vertical
        for (int i = 0; i < 10; i++) {
            if (tabuleiro[i][coluna] == 1) {
                tabuleiro[i][coluna] = 3;  // Marca a parte do navio
            }
        }
    }

    public static void imprimirTabuleiro(int[][] tabuleiro, boolean mostrarNavios) {
        System.out.print("   ");
        for (char letra = 'A'; letra <= 'J'; letra++) {
            System.out.printf("%-3s", letra);
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.printf("%-3d", i);
            for (int j = 0; j < 10; j++) {
                char simbolo;

                if (mostrarNavios) {
                    // Mostrar o navio completo se for um navio
                    if (tabuleiro[i][j] == 1) {
                        simbolo = 'N'; // Navio
                    } else {
                        simbolo = '*'; // Água
                    }
                } else {
                    if (tabuleiro[i][j] == 3) {
                        simbolo = 'X'; // Acerto no navio
                    } else if (tabuleiro[i][j] == 2) {
                        simbolo = 'O'; // Água
                    } else {
                        simbolo = '*'; // Oculto
                    }
                }

                System.out.printf("%-3s", simbolo);
            }
            System.out.println();
        }
    }

    public static boolean todosNaviosAfundados(int[][] tabuleiro) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabuleiro[i][j] == 1) {
                    return false;  // Se encontrar um navio ainda não afundado
                }
            }
        }
        return true;  // Todos os navios foram afundados
    }

    public static boolean realizarAtaqueComputador(int[][] tabuleiro, int[][] visao) {
        Random aleatorio = new Random();
        int linha = aleatorio.nextInt(10);
        int coluna = aleatorio.nextInt(10);

        while (visao[linha][coluna] == 2 || visao[linha][coluna] == 3) {
            linha = aleatorio.nextInt(10);
            coluna = aleatorio.nextInt(10);
        }

        System.out.println("Computador atirou em: " + linha + ", " + (char) ('A' + coluna));

        if (tabuleiro[linha][coluna] == 1) {
            System.out.println("ACERTOU UM NAVIO!");
            tabuleiro[linha][coluna] = 3;
            visao[linha][coluna] = 3;
            mostrarNavioCompleto(tabuleiro, linha, coluna);
            return true;
        } else {
            System.out.println("ÁGUA!");
            tabuleiro[linha][coluna] = 2;
            visao[linha][coluna] = 2;
            return false;
        }
    }
}
