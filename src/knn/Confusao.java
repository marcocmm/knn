/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

/**
 *
 * @author romulo
 */
public class Confusao {

    private int[][] matriz;
    private final int quantidadeInstancias;

    public Confusao(int numeroClasses, int quantidadeInstancias) {
        this.matriz = new int[numeroClasses][numeroClasses];
        this.quantidadeInstancias = quantidadeInstancias;
    }

    public int[][] getMatrizConfusao() {
        return this.matriz;
    }

    public void registrarConfusao(Classe expected, Classe actual) {
        this.matriz[Classe.toInt(expected) - 1][Classe.toInt(actual) - 1]++;
    }

    public double getAccuracy() {
        double sum = 0;
        for (int i = 0; i < matriz.length; i++) {
            sum += matriz[i][i];
        }
        return sum / quantidadeInstancias;
    }

    public void print() {
        for (int[] line : matriz) {
            for (int i : line) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }
    }

}
