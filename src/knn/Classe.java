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
public enum Classe {
    JANEIRO, FEVEREIRO, MARÇO, ABRIL, MAIO, JUNHO, JULHO, AGOSTO, SETEMBRO, OUTUBRO, NOVEMBRO, DEZEMBRO;

    public static Classe parseClasse(String classe) {
        switch (classe.toUpperCase()) {
            case "JANEIRO":
                return JANEIRO;
            case "FEVEREIRO":
                return FEVEREIRO;
            case "MARÇO":
                return MARÇO;
            case "ABRIL":
                return ABRIL;
            case "MAIO":
                return MAIO;
            case "JUNHO":
                return JUNHO;
            case "JULHO":
                return JULHO;
            case "AGOSTO":
                return AGOSTO;
            case "SETEMBRO":
                return SETEMBRO;
            case "OUTUBRO":
                return OUTUBRO;
            case "NOVEMBRO":
                return NOVEMBRO;
            case "DEZEMBRO":
                return DEZEMBRO;
            default:
                return null;
        }
    }

    public static int toInt(Classe classe) {
        switch (classe) {
            case JANEIRO:
                return 1;
            case FEVEREIRO:
                return 2;
            case MARÇO:
                return 3;
            case ABRIL:
                return 4;
            case MAIO:
                return 5;
            case JUNHO:
                return 6;
            case JULHO:
                return 7;
            case AGOSTO:
                return 8;
            case SETEMBRO:
                return 9;
            case OUTUBRO:
                return 10;
            case NOVEMBRO:
                return 11;
            case DEZEMBRO:
                return 12;
            default:
                return 0;
        }
    }
    
}
