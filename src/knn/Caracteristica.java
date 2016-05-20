/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

/**
 *
 * @author romulo
 */
public class Caracteristica {

    private double valor;

    public Caracteristica(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public Object clone() {
        return new Caracteristica(this.valor);
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
