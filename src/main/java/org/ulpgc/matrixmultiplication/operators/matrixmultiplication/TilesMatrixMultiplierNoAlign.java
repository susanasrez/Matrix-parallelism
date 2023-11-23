package org.ulpgc.matrixmultiplication.operators.matrixmultiplication;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionedMatrix;
import org.ulpgc.matrixmultiplication.operators.MatrixMultiplication;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TilesMatrixMultiplierNoAlign implements MatrixMultiplication {

    Matrix[][] result;

    public Matrix tilesMultiplication(PartitionedMatrix matrix_a, PartitionedMatrix matrix_b) throws InterruptedException {
        result = new Matrix[matrix_a.size()][matrix_a.size()];
        Matrix[][] a =  matrix_a.subPartitions;
        Matrix[][] b = matrix_b.subPartitions;
        int size = a.length;

        for (int i = 0; i < a.length; i++){
            Matrix[][] partialResult = new Matrix[size][size];

            ExecutorService executor = Executors.newFixedThreadPool(matrix_a.threads);

            try {
                int tasksCount = size * size;
                CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);
                for (int j = 0; j < a.length; j++) {
                    for (int k = 0; k < a.length; k++) {
                        int finalK = k;
                        int finalJ = j;
                        int finalI = i;
                        completionService.submit(() -> {
                            partialResult[finalJ][finalK] = multiply(a[finalJ][finalI], b[finalI][finalK]);
                            return null;
                        });
                    }
                }
                for (int t = 0; t < tasksCount; t++) {
                    completionService.take();
                }

                if (i == 0) {
                    result = partialResult;
                } else {
                    synchronized (this) {
                        add(partialResult);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        }
        return new PartitionedMatrix(matrix_a.originalSize, result, matrix_a.blockSize, matrix_a.threads, matrix_a.numAdded);
    }


    @Override
    public Matrix multiply(Matrix matrix_a, Matrix matrix_b) {
        double[][] c = new double[matrix_a.size()][matrix_b.size()];
        for (int i = 0; i < matrix_a.size(); i++) {
            for (int k = 0; k < matrix_b.size(); k++) {
                double aik = matrix_a.get(i, k);
                for (int j = 0; j < matrix_a.size();j++) {
                    c[i][j] += aik * matrix_b.get(k, j);
                }
            }
        }
        return new DenseMatrix(c);
    }

    public void add(Matrix[][] partialResult) {
        for (int i = 0; i < partialResult.length; i++) {
            for (int j = 0; j < partialResult.length; j++) {
                addPartial(partialResult[i][j], i, j);
            }
        }
    }

    public void addPartial(Matrix partialMatrix, int row, int col) {
        double[][] currentValues = ((DenseMatrix) partialMatrix).values();
        double[][] resultValues = ((DenseMatrix) result[row][col]).values();
        int blockSize = partialMatrix.size();

        for (int k = 0; k < blockSize; k++) {
            for (int l = 0; l < blockSize; l++) {
                resultValues[k][l] += currentValues[k][l];
            }
        }
    }


}

