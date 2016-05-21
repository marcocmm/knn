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
public class Distancia implements Comparable<Distancia> {

    private final Instancia from;
    private double distance;
    private final Instancia to;

    public Distancia(Instancia from, Instancia to) {
        this.from = from;
        this.to = to;
        this.distance = 0;
    }

    public Instancia getFrom() {
        return from;
    }

    public Instancia getTo() {
        return to;
    }

    private void setDistance(double distance) {
        this.distance = distance;
    }

    public void calculateEuclidienDistance() throws Exception {
        Iterator<Caracteristica> iteratorA;
        Iterator<Caracteristica> iteratorB;
        Caracteristica caracteristicaA;
        Caracteristica caracteristicaB;

        iteratorA = this.from.iterator();
        iteratorB = this.to.iterator();

        if (this.from.getQuantidadeCaracteristicas() != this.to.getQuantidadeCaracteristicas()) {
            throw new Exception("Vetores de tamanhos diferentes");
        }
        double sum = 0;
        while (iteratorA.hasNext() && iteratorB.hasNext()) {
            caracteristicaA = iteratorA.next();
            caracteristicaB = iteratorB.next();
            sum += Math.pow(caracteristicaA.getValor() - caracteristicaB.getValor(), 2);
        }
        setDistance(Math.sqrt(sum));
    }

    @Override
    public int compareTo(Distancia other) {
        return Double.compare(this.distance, other.distance);
    }

}
