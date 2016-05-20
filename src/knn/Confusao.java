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

    public Confusao(int numeroClasses) {
        this.matriz = new int[numeroClasses][numeroClasses];
    }

    public void registrarConfusao(Classe gave, Classe expected) {
        matriz[Classe.toInt(gave) - 1][Classe.toInt(expected) - 1]++;
    }
}
