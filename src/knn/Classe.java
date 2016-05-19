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

    public static Classe parseInt(int index) {
        switch (index) {
            case 1:
                return JANEIRO;
            case 2:
                return FEVEREIRO;
            case 3:
                return MARÇO;
            case 4:
                return ABRIL;
            case 5:
                return MAIO;
            case 6:
                return JUNHO;
            case 7:
                return JULHO;
            case 8:
                return AGOSTO;
            case 9:
                return SETEMBRO;
            case 10:
                return OUTUBRO;
            case 11:
                return NOVEMBRO;
            case 12:
                return DEZEMBRO;
            default:
                return null;
        }

    }
}
