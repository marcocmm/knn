/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import javafx.util.Pair;

/**
 *
 * @author romulo
 */
public class KNN {

    private final int k;
    private final Conjunto treino;
    private final Conjunto teste;
    private Confusao confusao;
    private Comparator<Pair<Double, Instancia>> comparator;

    public KNN(int k, Conjunto treino, Conjunto teste) {
        this.k = k;
        this.treino = (Conjunto) treino.clone();
        this.teste = (Conjunto) teste.clone();
        this.confusao = new Confusao(treino.getQuantidadeCaracteristicas(), treino.getQuantidadeInstancias());

        this.comparator = new Comparator<Pair<Double, Instancia>>() {
            @Override
            public int compare(Pair<Double, Instancia> a, Pair<Double, Instancia> b) {
                return Double.compare(a.getKey(), b.getKey());
            }
        };
    }

    public KNN(int k, InputStream treinoInputStream, InputStream testeInputStream) throws Exception {
        this.k = k;
        this.teste = parseInputStream(testeInputStream);
        this.treino = parseInputStream(treinoInputStream);
        this.confusao = new Confusao(treino.getQuantidadeCaracteristicas(), treino.getQuantidadeInstancias());
    }

    private Conjunto parseInputStream(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String strInstancia;
        List<Instancia> instancias;
        String[] strCaracteristicas;
        Caracteristica[] caracteristicas;
        Conjunto conjunto;

        if (inputStream == null) {
            throw new Exception("Arquivo não encontrado");
        }

        conjunto = null;
        instancias = new ArrayList();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
        try {
            while ((strInstancia = bufferedReader.readLine()) != null) {
                strCaracteristicas = strInstancia.replace(" ", "").split(",");

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
        Iterator<Instancia> instanciasTreino;
        Iterator<Instancia> instanciasTeste;
        Instancia instanciaTeste;
        Instancia instanciaTreino;
        List<Pair<Double, Instancia>> distancias;
        double distance;
        int[] votos;
        Classe classifiedAs;

        instanciasTreino = this.treino.iterator();
        instanciasTeste = this.teste.iterator();

        try {
            while (instanciasTeste.hasNext()) {
                instanciaTeste = instanciasTeste.next();
                distancias = new ArrayList();
                while (instanciasTreino.hasNext()) {
                    instanciaTreino = instanciasTreino.next();
                    distance = getEuclidienDistance(instanciaTeste, instanciaTreino);
                    distancias.add(new Pair(distance, instanciaTreino));
                }
                Collections.sort(distancias, this.comparator);
                votos = new int[12];
                for (int i = 0; i < k; i++) {
                    votos[Classe.toInt(distancias.get(0).getValue().getClasse()) - 1]++;
                    distancias.remove(0);
                }
                classifiedAs = Classe.parseInt(getIndexDoMaiorValor(votos) + 1);
                this.confusao.registrarConfusao(instanciaTeste.getClasse(), classifiedAs);
                instanciaTeste.setClasse(classifiedAs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int getIndexDoMaiorValor(int[] a) {
        int index = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[i - 1]) {
                index = i;
            }
        }
        return index;
    }

    public Confusao getMatrizConfusao() {
        return confusao;
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

    public double getEuclidienDistance(Instancia a, Instancia b) throws Exception {
        Iterator<Caracteristica> iteratorA;
        Iterator<Caracteristica> iteratorB;
        Caracteristica caracteristicaA;
        Caracteristica caracteristicaB;

        iteratorA = a.iterator();
        iteratorB = b.iterator();

        if (a.getQuantidadeCaracteristicas() != b.getQuantidadeCaracteristicas()) {
            throw new Exception("Vetores de tamanhos diferentes");
        }
        int sum = 0;
        while (iteratorA.hasNext() && iteratorB.hasNext()) {
            caracteristicaA = iteratorA.next();
            caracteristicaB = iteratorB.next();
            sum += Math.pow(caracteristicaA.getValor() - caracteristicaB.getValor(), 2);
        }
        return Math.sqrt(sum);
    }

}
