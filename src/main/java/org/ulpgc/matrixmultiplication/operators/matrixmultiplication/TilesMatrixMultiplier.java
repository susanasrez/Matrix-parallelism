package org.ulpgc.matrixmultiplication.operators.matrixmultiplication;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;
import org.ulpgc.matrixmultiplication.operators.Aligner;
import org.ulpgc.matrixmultiplication.operators.MatrixMultiplication;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TilesMatrixMultiplier implements MatrixMultiplication {

    Matrix[][] result;

    public PartitionMatrix tilesMultiplication(PartitionMatrix matrix_a, PartitionMatrix matrix_b){
        Matrix[][] aligned_A = Aligner.horizontalAlignmentInitial(matrix_a.subPartitions);
        Matrix[][] aligned_B = Aligner.verticalAlignmentInitial(matrix_b.subPartitions);

        int size = aligned_A.length;

        for(int iterations = 0; iterations < size; iterations++){
            Matrix[][] partialResult = new Matrix[size][size];

            ExecutorService executor = Executors.newFixedThreadPool(matrix_a.threads);
            try {
                int tasksCount = size * size;
                CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        final int row = i;
                        final int col = j;
                        Matrix[][] finalAligned_A = aligned_A;
                        Matrix[][] finalAligned_B = aligned_B;
                        completionService.submit(() -> {
                            partialResult[row][col] = multiply(finalAligned_A[row][col], finalAligned_B[row][col]);
                            return null;
                        });
                    }
                }

                for (int t = 0; t < tasksCount; t++) {
                    completionService.take();
                }

                if (iterations == 0) {
                    result = partialResult;
                } else {
                    synchronized (this) {
                        add(partialResult);
                    }
                }

                aligned_A = Aligner.horizontalAlign(aligned_A);
                aligned_B = Aligner.verticalAlign(aligned_B);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        }
        return new PartitionMatrix(matrix_a.originalSize,result, matrix_a.blockSize, matrix_a.threads, matrix_a.numAdded);
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
