/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.util.Iterator;

/**
 *
 * @author romulo
 */
public class Conjunto implements Iterable<Instancia> {

    private final Instancia[] instancias;

    public Conjunto(Instancia[] instancias) {
        this.instancias = new Instancia[instancias.length];
        for (int i = 0; i < instancias.length; i++) {
            this.instancias[i] = (Instancia) instancias[i].clone();
        }
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
        return new Conjunto(this.instancias);
    }

}
