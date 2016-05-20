/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class Conjunto implements Iterable<Instancia> {

    private final Instancia[] instancias;
    private final int quantidadeCaracteristicas;

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
        for (int i = 0; i < instancias.length; i++) {
            this.instancias[i] = (Instancia) instancias[i].clone();
        }
    }

    public int getQuantidadeInstancias() {
        return instancias.length;
    }

    public int getQuantidadeCaracteristicas() {
        return this.quantidadeCaracteristicas;
    }

    @Deprecated
    private Conjunto(int quantidadeInstancias, int quantidadeCaracteristicas) {
        this.instancias = new Instancia[quantidadeInstancias];
        this.quantidadeCaracteristicas = quantidadeCaracteristicas;
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
        try {
            return new Conjunto(this.instancias);
        } catch (Exception ex) {
            Logger.getLogger(Conjunto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
