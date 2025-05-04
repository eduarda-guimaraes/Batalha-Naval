package jogo;

import java.util.Scanner;
import java.util.Random;

public class BatalhaNaval {
    public static void main(String[] args) {
        // Criando objetos para ler dados e gerar números aleatórios
        Scanner ler = new Scanner(System.in);
        Random aleatorio = new Random();

        // Criando os tabuleiros dos jogadores e das visões dos tabuleiros
        int[][] tabuleiroJogador1 = new int[10][10];
        int[][] tabuleiroJogador2 = new int[10][10];
        int[][] visaoJogador1 = new int[10][10];
        int[][] visaoJogador2 = new int[10][10];

        // Definindo os tamanhos dos navios (4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
        int[] tamanhosNavios = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        System.out.println("BATALHA NAVAL");
        mostrarLegenda();  // Exibe as legendas do jogo

        // Escolha do modo de jogo
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Jogador vs Computador");
        System.out.println("2 - Jogador vs Jogador");
        System.out.print("Digite 1 ou 2: ");
        int modo = ler.nextInt();
        ler.nextLine();  // Limpa o buffer

        // Nome dos jogadores
        System.out.print("Nome do Jogador 1: ");
        String jogador1 = ler.nextLine();
        String jogador2;

        if (modo == 1) {
            jogador2 = "Computador";  // Se for modo computador, o segundo jogador é o computador
        } else {
            System.out.print("Nome do Jogador 2: ");
            jogador2 = ler.nextLine();
        }

        // Posicionamento dos navios do jogador 1 (manual ou automático)
        System.out.println("\n" + jogador1 + ", deseja posicionar seus navios manualmente?");
        System.out.print("Digite S para sim ou N para não (posicionamento automático): ");
        char resposta1 = ler.next().toUpperCase().charAt(0);
        ler.nextLine();  // Limpa o buffer

        if (resposta1 == 'S') {
            System.out.println(jogador1 + ", posicione seus navios:");
            posicionarNavios(ler, tabuleiroJogador1, tamanhosNavios);  // Função para posicionar navios
        } else {
            posicionarNaviosComputador(tabuleiroJogador1, tamanhosNavios);  // Posicionamento automático
            System.out.println("Navios posicionados automaticamente para " + jogador1 + ".");
        }

        // Posicionamento dos navios do jogador 2 (manual ou automático)
        if (modo == 1) {
            posicionarNaviosComputador(tabuleiroJogador2, tamanhosNavios);  // O computador posiciona seus navios automaticamente
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
                posicionarNaviosComputador(tabuleiroJogador2, tamanhosNavios);  // Posicionamento automático
                System.out.println("Navios posicionados automaticamente para " + jogador2 + ".");
            }
        }

        // Loop principal do jogo
        boolean vezJogador1 = true;  // Define a vez do jogador 1
        boolean jogoAcabou = false;

        while (!jogoAcabou) {
            if (vezJogador1) {
                System.out.println("\nTabuleiro do oponente:");
                imprimirTabuleiro(visaoJogador2, false);  // Exibe a visão do jogador 1 sobre o tabuleiro do jogador 2

                boolean acertou = realizarAtaque(ler, tabuleiroJogador2, visaoJogador2);  // Jogador 1 atira no tabuleiro do jogador 2

                if (todosNaviosAfundados(tabuleiroJogador2)) {  // Verifica se todos os navios de jogador 2 foram afundados
                    System.out.println("\nPARABÉNS " + jogador1 + "! VOCÊ VENCEU!");
                    jogoAcabou = true;
                } else if (!acertou) {
                    vezJogador1 = false;  // Se não acertou, muda para a vez do jogador 2
                }
            } else {
                System.out.println("\nVez de " + jogador2);
                System.out.println("Tabuleiro do oponente:");
                imprimirTabuleiro(visaoJogador1, false);

                boolean acertou;
                if (modo == 1) {
                    acertou = realizarAtaqueComputador(tabuleiroJogador1, visaoJogador1);  // Computador atira no jogador 1
                } else {
                    acertou = realizarAtaque(ler, tabuleiroJogador1, visaoJogador1);  // Jogador 2 atira no jogador 1
                }

                if (todosNaviosAfundados(tabuleiroJogador1)) {
                    System.out.println("\nPARABÉNS " + jogador2 + "! VOCÊ VENCEU!");
                    jogoAcabou = true;
                } else if (!acertou) {
                    vezJogador1 = true;  // Se não acertou, muda para a vez do jogador 1
                }
            }
        }
    }

    // Mostra a legenda do jogo (o que cada símbolo significa)
    public static void mostrarLegenda() {
        System.out.println("\nLEGENDA:");
        System.out.println("* - Água");
        System.out.println("N - Navio (durante o posicionamento)");
        System.out.println("O - Tiro na água");
        System.out.println("X - Tiro no navio (acerto)");
        System.out.println("----------------------");
    }

    // Função para posicionar os navios manualmente
    public static void posicionarNavios(Scanner ler, int[][] tabuleiro, int[] tamanhosNavios) {
        for (int tamanho : tamanhosNavios) {
            imprimirTabuleiro(tabuleiro, true);
            System.out.println("Posicionando navio de tamanho " + tamanho);

            boolean posicionado = false;
            while (!posicionado) {
                // Leitura da posição e direção do navio
                System.out.print("Linha (0-9): ");
                int linha = ler.nextInt();
                System.out.print("Coluna (A-J): ");
                char colLetra = ler.next().toUpperCase().charAt(0);
                int coluna = colLetra - 'A';

                System.out.print("Direção (1-Horizontal, 2-Vertical): ");
                int direcao = ler.nextInt();
                ler.nextLine();  // Limpa o buffer

                posicionado = verificarPosicao(tabuleiro, linha, coluna, tamanho, direcao);

                if (posicionado) {
                    // Posiciona o navio no tabuleiro
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

    // Função para posicionar os navios automaticamente
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

    // Verifica se é possível posicionar o navio na posição escolhida
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

    // Função para realizar um ataque
    public static boolean realizarAtaque(Scanner ler, int[][] tabuleiro, int[][] visao) {
        int linha, coluna;

        while (true) {
            System.out.print("Linha (0-9): ");
            linha = ler.nextInt();
            System.out.print("Coluna (A-J): ");
            char colLetra = ler.next().toUpperCase().charAt(0);
            coluna = colLetra - 'A';
            ler.nextLine();  // Limpa o buffer

            // Verifica se a posição é válida e se não foi atacada antes
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

        // Se acertar um navio
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

    // Método para mostrar o navio completo (horizontal e vertical) após o acerto
    public static void mostrarNavioCompleto(int[][] tabuleiro, int linha, int coluna) {
        // Verificar horizontalmente
        for (int i = 0; i < 10; i++) {
            if (tabuleiro[linha][i] == 1) {
                tabuleiro[linha][i] = 3;  // Marca como parte do navio
            }
        }

        // Verificar verticalmente
        for (int i = 0; i < 10; i++) {
            if (tabuleiro[i][coluna] == 1) {
                tabuleiro[i][coluna] = 3;  // Marca como parte do navio
            }
        }
    }

    // Função para imprimir o tabuleiro
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
                    if (tabuleiro[i][j] == 1) {
                        simbolo = 'N'; // Navio
                    } else {
                        simbolo = '*'; // Água
                    }
                } else {
                    if (tabuleiro[i][j] == 3) {
                        simbolo = 'X'; // Acerto
                    } else if (tabuleiro[i][j] == 2) {
                        simbolo = 'O'; // Água
                    } else {
                        simbolo = '*'; // Água
                    }
                }

                System.out.printf("%-3c", simbolo);
            }
            System.out.println();
        }
    }

    // Função para verificar se todos os navios de um jogador foram afundados
    public static boolean todosNaviosAfundados(int[][] tabuleiro) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabuleiro[i][j] == 1) {
                    return false;  // Ainda existe um navio no tabuleiro
                }
            }
        }
        return true;  // Todos os navios foram afundados
    }

    // Função para realizar o ataque do computador
    public static boolean realizarAtaqueComputador(int[][] tabuleiro, int[][] visao) {
        Random aleatorio = new Random();

        int linha = aleatorio.nextInt(10);
        int coluna = aleatorio.nextInt(10);

        System.out.println("Computador atirou na posição " + linha + ", " + (char) ('A' + coluna));

        // Se acertar um navio
        if (tabuleiro[linha][coluna] == 1) {
            System.out.println("O COMPUTADOR ACERTOU UM NAVIO!");
            tabuleiro[linha][coluna] = 3;  // Marca o acerto
            visao[linha][coluna] = 3;      // Atualiza a visão do jogador
            return true;
        } else {
            System.out.println("O COMPUTADOR ATIROU NA ÁGUA!");
            tabuleiro[linha][coluna] = 2;  // Marca como água
            visao[linha][coluna] = 2;      // Atualiza a visão do jogador
            return false;
        }
    }
}
