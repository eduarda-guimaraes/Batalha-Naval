package jogo;

import java.util.Scanner;
import java.util.Random;

public class BatalhaNaval {
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        Random aleatorio = new Random();
        int[][] tabela = new int[10][10];

        // Cabeçalho das colunas (A a J)
        System.out.print("   ");
        for (char coluna = 'A'; coluna <= 'J'; coluna++) {
            System.out.printf("%-3s", coluna);
        }
        System.out.println();

        // Impressão da matriz com números de linha e asteriscos alinhados
        for (int l = 0; l < tabela.length; l++) {
            System.out.printf("%-3d", l); // número da linha
            for (int c = 0; c < tabela[0].length; c++) {
                tabela[l][c] = aleatorio.nextInt(1, 100);
                System.out.printf("%-3s", "*");
            }
            System.out.println();
        }
    }
}