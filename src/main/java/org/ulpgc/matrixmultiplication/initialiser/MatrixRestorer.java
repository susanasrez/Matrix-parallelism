package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;

public class MatrixRestorer {

    public static Matrix resetMatrix(Matrix matrix){
        PartitionMatrix partitionMatrix = (PartitionMatrix) matrix;
        DenseMatrix denseMatrix = (DenseMatrix) convertToDenseMatrix(partitionMatrix.subPartitions);
        return originalMatrix(denseMatrix, partitionMatrix.numAdded);
    }
    public static Matrix convertToDenseMatrix(Matrix[][] subPartitions) {
        int numRows = subPartitions.length;
        int numCols = subPartitions[0].length;
        int blockSize = subPartitions[0][0].size();

        int totalRows = numRows * blockSize;
        int totalCols = numCols * blockSize;

        double[][] values = new double[totalRows][totalCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                DenseMatrix denseSubMatrix = (DenseMatrix) subPartitions[i][j];
                double[][] subValues = denseSubMatrix.values();
                for (int k = 0; k < blockSize; k++) {
                    System.arraycopy(subValues[k], 0, values[i * blockSize + k], j * blockSize, blockSize);
                }
            }
        }

        return new DenseMatrix(values);
    }


    public static DenseMatrix originalMatrix(Matrix matrix, int numAdded){
        DenseMatrix matrixDense = (DenseMatrix) matrix;
        double[][] values = matrixDense.values();
        double[][] trimmedValues = new double[matrix.size()-numAdded][matrix.size()-numAdded];

        for (int i = 0; i < matrix.size()-numAdded; i++) {
            System.arraycopy(values[i], 0, trimmedValues[i], 0, matrix.size()-numAdded);
        }

        return new DenseMatrix(trimmedValues);
    }
}
