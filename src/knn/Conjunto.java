/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author romulo
 */
public final class Conjunto implements Iterable<Instancia> {

    private Instancia[] instancias;
    private final int quantidadeCaracteristicas;
    private List<Classe> classes;

    public Conjunto(Instancia[] instancias) throws Exception {
        if (instancias == null) {
            throw new Exception("Instâncias cannot be null");
        }
        if (instancias.length == 0) {
            throw new Exception("Instâncias must be at least one");
        }
        if (instancias[0] == null) {
            throw new Exception("Características cannot be null");
        }
        this.instancias = new Instancia[instancias.length];
        this.quantidadeCaracteristicas = instancias[0].getQuantidadeCaracteristicas();
        this.classes = new ArrayList<>();
        for (int i = 0; i < instancias.length; i++) {
            if (!this.classes.contains(instancias[i].getClasse())) {
                this.classes.add(instancias[i].getClasse());
            }
            this.instancias[i] = (Instancia) instancias[i].clone();
        }
    }

    public void normalizarMinMax() {
        Caracteristica[][] listMinMax = new Caracteristica[2][getQuantidadeCaracteristicas()];
        int index = 0;
        for (Caracteristica caracteristica : instancias[0]) {
            listMinMax[0][index] = caracteristica;
            listMinMax[1][index] = caracteristica;
            index++;
        }

        for (Instancia instancia : this) {
            index = 0;
            for (Caracteristica caracteristica : instancia) {
                if (caracteristica.getValor() < listMinMax[0][index].getValor()) {

                    listMinMax[0][index] = caracteristica;
                } else if (caracteristica.getValor() > listMinMax[1][index].getValor()) {
                    listMinMax[1][index] = caracteristica;
                }
                index++;
            }
        }
        double novoValor, minino, maximo;
        for (Instancia instancia : this) {
            index = 0;
            for (Caracteristica caracteristica : instancia) {
                minino = listMinMax[0][index].getValor();
                maximo = listMinMax[1][index].getValor();
                novoValor = ((caracteristica.getValor() - minino) / (maximo - minino));
                caracteristica.setValor(novoValor);
                index++;
            }
        }
    }

    public void separarInstancias(int porcentagem) {
        int instanciasASeremExcluidas;
        Random gerador = new Random();
        int numeroInstancia;

        instanciasASeremExcluidas = (int) (this.getQuantidadeInstancias() * (1.0 - (porcentagem / 100.0)));
        for (int i = 0; i < instanciasASeremExcluidas; i++) {
            numeroInstancia = gerador.nextInt(getQuantidadeInstancias());
            excluirInstancia(numeroInstancia);
        }
    }

    private void excluirInstancia(int index) {
        Instancia[] novaListaInstancias = new Instancia[this.getQuantidadeInstancias() - 1];
        int contador = 0;
        for (int i = 0; i < this.instancias.length; i++) {
            if (index != i) {
                novaListaInstancias[contador] = (Instancia) instancias[i].clone();
                contador++;
            }
        }
        setInstancias(novaListaInstancias);
    }

    public int getQuantidadeInstancias() {
        return instancias.length;
    }

    public int getQuantidadeCaracteristicas() {
        return this.quantidadeCaracteristicas;
    }

    public void setInstancias(Instancia[] instancias) {
        this.instancias = instancias.clone();
    }

    public int getQuantidadeClasses() {
        return this.classes.size() + 1;
    }

    public void print() {
        for (int i = 0; i < 5; i++) {
            Instancia instancia = instancias[i];
            instancia.print();
        }
    }

    @Override
    public Iterator<Instancia> iterator() {
        return new Iterator<Instancia>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < instancias.length;
            }

            @Override
            public Instancia next() {
                return instancias[this.index++];
            }
        };
    }

    @Override
    protected Object clone() {
        try {
            return new Conjunto(this.instancias);
        } catch (Exception ex) {
            Logger.getLogger(Conjunto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
