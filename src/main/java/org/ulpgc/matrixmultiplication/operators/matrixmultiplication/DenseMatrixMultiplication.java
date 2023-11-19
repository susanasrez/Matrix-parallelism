package org.ulpgc.matrixmultiplication.operators.matrixmultiplication;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.operators.MatrixMultiplication;

public class DenseMatrixMultiplication implements MatrixMultiplication {
    @Override
    public Matrix multiply(Matrix matrix_a, Matrix matrix_b) {
        DenseMatrix a = (DenseMatrix) matrix_a;
        DenseMatrix b = (DenseMatrix) matrix_b;
        double[][] c = new double[a.size()][b.size()];
        for (int i = 0; i < a.size(); i++) {
            for (int k = 0; k < b.size(); k++) {
                double aik = a.get(i, k);
                for (int j = 0; j < a.size();j++) {
                    c[i][j] += aik * b.get(k, j);
                }
            }
        }
        return new DenseMatrix(c);
    }
}
