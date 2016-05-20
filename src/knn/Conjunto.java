/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author romulo
 */
public final class Conjunto implements Iterable<Instancia> {

    private Instancia[] instancias;

    public Conjunto(Instancia[] instancias, int porcentagem) {
        this.instancias = new Instancia[instancias.length];
        for (int i = 0; i < instancias.length; i++) {
            this.instancias[i] = (Instancia) instancias[i].clone();
        }
        normalizarMinMax();
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
            novoNumeroInstacias = this.getQuantidadeInstancias() / 4;
            for (int i = 0; i < novoNumeroInstacias; i++) {
                numeroInstancia = gerador.nextInt(novoNumeroInstacias);
                novoNumeroInstacias--;
                excluirInstacia(numeroInstancia);
            }
        } else if (porcentagem == 50) {
            novoNumeroInstacias = this.getQuantidadeInstancias() / 2;
            for (int i = 0; i < novoNumeroInstacias; i++) {
                numeroInstancia = gerador.nextInt(novoNumeroInstacias);
                novoNumeroInstacias--;
                excluirInstacia(numeroInstancia);
            }
        }
    }

    public void excluirInstacia(int index) {
        Instancia[] novaListaInstancias = new Instancia[this.getQuantidadeInstancias() - 1];
        instancias[index] = instancias[this.getQuantidadeInstancias()];
        int contador = 0;
        for (Instancia ins : this.instancias) {
            novaListaInstancias[contador] = ins;
            contador++;
        }
        setInstancias(novaListaInstancias);
    }

    public int getQuantidadeInstancias() {
        return instancias.length;
    }

    public int getQuantidadeCaracteristicas() {
        return instancias[0].getQuantidadeCaracteristicas();
    }

    @Deprecated
    private Conjunto(int quantidadeInstancias, int quantidadeCaracteristicas) {
        this.instancias = new Instancia[quantidadeInstancias];
        for (int i = 0; i < this.instancias.length; i++) {
            this.instancias[i] = new Instancia(new Caracteristica[quantidadeCaracteristicas]);
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
        return new Conjunto(this.instancias, 100);
    }

    public void setInstancias(Instancia[] instancias) {
        this.instancias = instancias;
    }

}
