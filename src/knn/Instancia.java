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
public class Instancia implements Iterable<Caracteristica> {

    private final Caracteristica[] caracteristicas;
    private Classe classe;

    public Instancia(Caracteristica[] caracteristicas, Classe classe) {
        this.caracteristicas = new Caracteristica[caracteristicas.length];
        for (int i = 0; i < caracteristicas.length; i++) {
            this.caracteristicas[i] = (Caracteristica) caracteristicas[i].clone();
        }
        this.classe = classe;
    }

    public Instancia(Caracteristica[] caracteristicas) {
        this.caracteristicas = new Caracteristica[caracteristicas.length];
        for (int i = 0; i < caracteristicas.length; i++) {
            this.caracteristicas[i] = (Caracteristica) caracteristicas[i].clone();
        }
        this.classe = null;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Classe getClasse() {
        return classe;
    }

    public int getQuantidadeCaracteristicas() {
        return caracteristicas.length;
    }

    public void print() {
        for (Caracteristica caracteristica : this) {
            caracteristica.print();
        }
        System.out.println();
    }

    @Override
    public Iterator<Caracteristica> iterator() {
        return new Iterator<Caracteristica>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < caracteristicas.length;
            }

            @Override
            public Caracteristica next() {
                return caracteristicas[this.index++];
            }
        };
    }

    @Override
    public Object clone() {
        return new Instancia(this.caracteristicas, this.classe);
    }

}
