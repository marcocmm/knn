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

    public Conjunto(Instancia[] instancias, int porcentagem) throws Exception {
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
        separarDados(porcentagem);
        System.out.println("Qtd instancias: " + getQuantidadeInstancias());
//        normalizarMinMax();
    }

    @Deprecated
    private Conjunto(int quantidadeInstancias, int quantidadeCaracteristicas) {
        this.instancias = new Instancia[quantidadeInstancias];
        this.quantidadeCaracteristicas = quantidadeCaracteristicas;
        for (int i = 0; i < this.instancias.length; i++) {
            this.instancias[i] = new Instancia(new Caracteristica[quantidadeCaracteristicas]);
        }
    }

    public void normalizarMinMax() {
        Caracteristica[][] listMinMax = new Caracteristica[2][instancias[0].getQuantidadeCaracteristicas()];
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
        double novoValor;
        for (Instancia instancia : this) {
            index = 0;
            for (Caracteristica caracteristica : instancia) {
                novoValor = ((caracteristica.getValor() - listMinMax[0][index].getValor()) / (listMinMax[1][index].getValor() - listMinMax[0][index].getValor()));
                caracteristica.setValor(novoValor);
                index++;
            }
        }
    }

    public void separarDados(int porcentagem) {
        int novoNumeroInstacias;
        Random gerador = new Random();
        int numeroInstancia;

        if (porcentagem == 25) {
            novoNumeroInstacias = (this.getQuantidadeInstancias() / 4)*3;
            for (int i = 0; i < novoNumeroInstacias; i++) {
                numeroInstancia = gerador.nextInt(getQuantidadeInstancias());
                excluirInstancia(numeroInstancia);
            }
        } else if (porcentagem == 50) {
            novoNumeroInstacias = this.getQuantidadeInstancias() / 2;
            for (int i = 0; i < novoNumeroInstacias; i++) {
                numeroInstancia = gerador.nextInt(getQuantidadeInstancias());
                excluirInstancia(numeroInstancia);
            }
        }
    }

    public void excluirInstancia(int index) {
        Instancia[] novaListaInstancias = new Instancia[this.getQuantidadeInstancias() - 1];
        int contador = 0;
        for (Instancia ins : this.instancias) {
            if (!(contador == index)) {
                novaListaInstancias[contador] = ins;
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
        this.instancias = instancias;
    }

    public int getQuantidadeClasses() {
        return this.classes.size() + 1;
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
            return new Conjunto(this.instancias, 100);
        } catch (Exception ex) {
            Logger.getLogger(Conjunto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
