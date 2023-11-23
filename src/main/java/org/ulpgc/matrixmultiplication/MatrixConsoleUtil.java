package org.ulpgc.matrixmultiplication;

import org.ulpgc.matrixmultiplication.consoleoutput.TimeResults;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;

public interface MatrixConsoleUtil {
    TimeResults setUp(int n) throws InterruptedException;
    Matrix time_mult_tiles(DenseMatrix denseMatrix) throws InterruptedException;
    Matrix time_mult_dense(DenseMatrix denseMatrix);
}
