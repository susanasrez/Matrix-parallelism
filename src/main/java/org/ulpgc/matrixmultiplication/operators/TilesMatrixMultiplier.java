package org.ulpgc.matrixmultiplication.operators;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TilesMatrixMultiplier implements MatrixMultiplication {
    @Override
    public Matrix multiply(Matrix matrix_a, Matrix matrix_b) {
        PartitionMatrix matrixA = (PartitionMatrix) matrix_a;
        PartitionMatrix matrixB = (PartitionMatrix) matrix_a;

        int blockSize = matrixA.blockSize;
        int numThreads = matrixA.threads;

        DenseMatrix originalMatrixA = (DenseMatrix) matrixA.matrix;
        DenseMatrix originalMatrixB = (DenseMatrix) matrixB.matrix;

        double[][] result = new double[matrixA.size()][matrixA.size()];

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < matrixA.size(); i += blockSize) {
            for (int j = 0; j < matrixA.size(); j += blockSize) {
                final int row = i;
                final int col = j;
                executor.submit(() -> multiplyBlocks(originalMatrixA, originalMatrixB, result, row, col, blockSize));
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new PartitionMatrix(new DenseMatrix(result), blockSize, numThreads, false, 0);
    }

    private void multiplyBlocks(DenseMatrix matrixA, DenseMatrix matrixB, double[][] result, int startRow, int startCol, int blockSize) {
        for (int i = startRow; i < startRow + blockSize; i++) {
            for (int k = 0; k < matrixA.size(); k++) {
                double aik = matrixA.get(i, k);
                for (int j = startCol; j < startCol + blockSize; j++) {
                    result[i][j] += aik * matrixB.get(k, j);
                }
            }
        }
    }
}
