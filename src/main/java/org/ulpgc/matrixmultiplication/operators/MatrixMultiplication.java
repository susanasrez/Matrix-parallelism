package org.ulpgc.matrixmultiplication.operators;

import org.ulpgc.matrixmultiplication.Matrix;

public interface MatrixMultiplication {
    Matrix multiply(Matrix matrix_a, Matrix matrix_b);
}
