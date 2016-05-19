/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class KNN {

    private final int k;
    private final Conjunto treino;
    private final Conjunto teste;

    public KNN(int k, Conjunto treino, Conjunto teste) {
        this.k = k;
        this.treino = (Conjunto) treino.clone();
        this.teste = (Conjunto) teste.clone();
    }

    public KNN(int k, InputStream treinoInputStream, InputStream testeInputStream) {
        this.k = k;
        this.teste = parseInputStream(testeInputStream);
        this.treino = parseInputStream(treinoInputStream);
    }

    private Conjunto parseInputStream(InputStream inputStream) {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String strInstancia;
        List<Instancia> instancias;
        String[] strCaracteristicas;
        Caracteristica[] caracteristicas;
        Conjunto conjunto;

        conjunto = null;
        strInstancia = "";
        instancias = new ArrayList();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
        try {
            while (strInstancia != null) {
                strInstancia = bufferedReader.readLine();
                strCaracteristicas = strInstancia.split(", ");

                caracteristicas = new Caracteristica[strCaracteristicas.length - 1];
                for (int i = 0; i < strCaracteristicas.length - 1; i++) {
                    caracteristicas[i] = new Caracteristica(Double.parseDouble(strCaracteristicas[i]));
                }
                instancias.add(new Instancia(caracteristicas, Classe.parseClasse(strCaracteristicas[strCaracteristicas.length - 1])));
            }
            conjunto = new Conjunto(instancias.toArray(new Instancia[instancias.size()]));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return conjunto;
    }

    public void classify() {
        for (Instancia instancia : this.treino) {
            for (Caracteristica caracteristica : instancia) {

            }
        }
        throw new UnsupportedOperationException();
    }

    public double getEuclidienDistance(double[] a, double[] b) throws Exception {
        if (a.length != b.length) {
            throw new Exception("Vetores de tamanhos diferentes");
        }
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);

        }
        return Math.sqrt(sum);
    }

}
