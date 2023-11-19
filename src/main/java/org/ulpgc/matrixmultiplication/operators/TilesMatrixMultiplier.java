package org.ulpgc.matrixmultiplication.operators;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.initialiser.Aligner;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;


public class TilesMatrixMultiplier implements MatrixMultiplication {

    Matrix[][] result;

    public Matrix[][] tilesMultiplication(Matrix[][] matrix_a, Matrix[][] matrix_b){
        Matrix[][] aligned_A = Aligner.horizontalAlignmentInitial(matrix_a);
        Matrix[][] aligned_B = Aligner.verticalAlignmentInitial(matrix_b);

        int size = aligned_A.length;

        for(int iterations = 0; iterations < size; iterations++){
            Matrix[][] partialResult = new Matrix[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    partialResult[i][j] = multiply(aligned_A[i][j], aligned_B[i][j]);
                }
            }
            if(iterations == 0){
                result = partialResult;
            }
            else {
                add(partialResult);
            }
            aligned_A = Aligner.horizontalAlign(aligned_A);
            aligned_B = Aligner.verticalAlign(aligned_B);
        }
        return result;
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
