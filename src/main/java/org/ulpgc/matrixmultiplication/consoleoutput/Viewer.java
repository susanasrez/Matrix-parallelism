package org.ulpgc.matrixmultiplication.consoleoutput;

import org.ulpgc.matrixmultiplication.Matrix;

public class Viewer {

    public static void showDense(Matrix c){
        int size = c.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < c.size(); j++) {
                System.out.print(c.get(i, j) + " ");
            }
            System.out.println();
        }
    }


}
