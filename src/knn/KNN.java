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
    private final boolean normalizar;

    public KNN(int k, InputStream treinoInputStream, int porcentagem, boolean normalizar) throws Exception {
        this.k = k;
        this.treino = parseInputStream(treinoInputStream);
        this.treino.separarInstancias(porcentagem);
        this.normalizar = normalizar;
        if (this.normalizar) {
            this.treino.normalizarMinMax();
        }
    }

    public void setConjuntoTeste(InputStream testeInputStream) throws Exception {
        this.teste = parseInputStream(testeInputStream);
        if (this.normalizar) {
            this.teste.normalizarMinMax();
        }
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
        Classe classifiedAs;

        this.confusao = new Confusao(this.treino.getQuantidadeClasses(), this.teste.getQuantidadeInstancias());

        for (Instancia instanciaTeste : this.teste) {
            classifiedAs = classify(instanciaTeste);
            this.confusao.registrarConfusao(instanciaTeste.getClasse(), classifiedAs);
            instanciaTeste.setClasse(classifiedAs);
        }
    }

    public Classe classify(Instancia instancia) {
        List<Distancia> distancias;
        Distancia distancia;
        int[] votos;

        try {
            distancias = new ArrayList();
            for (Instancia instanciaTreino : this.treino) {
                distancia = new Distancia(instancia, instanciaTreino);
                distancia.calculateEuclidienDistance();
                distancias.add(distancia);

            }
            Collections.sort(distancias);
            votos = new int[this.treino.getQuantidadeClasses()];
            if (distancias.size() <= k) {
                throw new Exception("Vetor de distâncias não preenchido");
            }
            for (int i = 0; i < k; i++) {
                votos[Classe.toInt(distancias.get(0).getTo().getClasse()) - 1]++;
                distancias.remove(0);
            }
            return Classe.parseInt(getIndexDoMaiorValor(votos) + 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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
