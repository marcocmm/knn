/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.io.InputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author marco
 */
public class KNNTest {

    public KNNTest() {
    }

    @Test
    public void testSomeMethod() {
        InputStream treinoInputStream = getClass().getResourceAsStream("/conjuntos/treino.data");
        InputStream testeInputStream = getClass().getResourceAsStream("/conjuntos/teste.data");
        KNN knn;
        try {
            knn = new KNN(3, treinoInputStream, testeInputStream);
            knn.classify();
            knn.getMatrizConfusao().getAccuracy();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

}
