package org.ulpgc.matrixmultiplication.checker;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.operators.MatrixMultiplication;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.DenseMatrixMultiplication;

public class Checker implements MatrixMultiplicationChecker{

    @Override
    public boolean test(Matrix a , Matrix b, Matrix c){
        MatrixMultiplication multiplier = new DenseMatrixMultiplication();
        Matrix ab = multiplier.multiply(a,b);
        Matrix ab_c = multiplier.multiply(ab, c);
        Matrix bc = multiplier.multiply(b,c);
        Matrix a_bc = multiplier.multiply(a,bc);
        return areMatricesEqual(ab_c, a_bc);
    }

    @Override
    public boolean areMatricesEqual(Matrix ab_c, Matrix a_bc) {
        double epsilon = 1E-8;
        DenseMatrix a = (DenseMatrix) ab_c;
        DenseMatrix b = (DenseMatrix) a_bc;
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.size(); j++) {
                if ((Math.abs(a.get(i, j) - b.get(i, j)) > epsilon)) {
                    return false;
                }
            }
        }
        return true;
    }

}
