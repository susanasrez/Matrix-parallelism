package org.ulpgc.matrixmultiplication.checker;

import org.ulpgc.matrixmultiplication.Matrix;

public interface MatrixMultiplicationChecker {
    boolean test(Matrix a, Matrix b, Matrix c);

    boolean areMatricesEqual(Matrix ab_c, Matrix a_bc);
}
