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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author romulo
 */
public class KNN {

    private final int k;
    private final Conjunto treino;
    private Conjunto teste;
    private Confusao confusao;

    public KNN(int k, Conjunto treino) {
        this.k = k;
        this.treino = (Conjunto) treino.clone();
    }

    public KNN(int k, InputStream treinoInputStream) throws Exception {
        this.k = k;
        this.treino = parseInputStream(treinoInputStream);
    }

    public void setConjuntoTeste(Conjunto teste) {
        this.teste = (Conjunto) teste.clone();
    }

    public void setConjuntoTeste(InputStream testeInputStream) throws Exception {
        this.teste = parseInputStream(testeInputStream);
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
        List<Distancia> distancias;
        Distancia distancia;
        int[] votos;
        Classe classifiedAs;

        this.confusao = new Confusao(this.treino.getQuantidadeClasses(), this.teste.getQuantidadeInstancias());
        instanciasTeste = this.teste.iterator();

        try {
            while (instanciasTeste.hasNext()) {
                instanciaTeste = instanciasTeste.next();
                instanciasTreino = this.treino.iterator();
                distancias = new ArrayList();
                while (instanciasTreino.hasNext()) {
                    instanciaTreino = instanciasTreino.next();
                    distancia = new Distancia(instanciaTeste, instanciaTreino);
                    distancia.calculateEuclidienDistance();
                    distancias.add(distancia);
                }
                Collections.sort(distancias);
                votos = new int[this.treino.getQuantidadeClasses()];
                for (int i = 0; i < k; i++) {
                    votos[Classe.toInt(distancias.get(0).getTo().getClasse()) - 1]++;
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
}
