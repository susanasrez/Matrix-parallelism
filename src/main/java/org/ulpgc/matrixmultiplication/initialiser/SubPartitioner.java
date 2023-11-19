package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;


public class SubPartitioner {

    public static Matrix[][] createSubPartitions(Matrix matrix, int threads, int blockSize){
        int dimension = (int) Math.sqrt(threads);
        Matrix[][] subPartitions =new Matrix[dimension][dimension];

        int size = matrix.size();

        for (int i = 0; i < size; i += blockSize) {
            for (int j = 0; j < size; j += blockSize) {
                int subSize = Math.min(blockSize, size - i);
                Matrix subPartition = getSubMatrix((DenseMatrix) matrix, i, j, subSize);
                subPartitions[i / blockSize][j / blockSize] = subPartition;
            }
        }

        return subPartitions;
    }

    public static Matrix getSubMatrix(DenseMatrix matrix, int startRow, int startCol, int subSize){
        double[][] subValues = new double[subSize][subSize];
        for (int i = 0; i < subSize; i++) {
            for (int j = 0; j < subSize; j++) {
                subValues[i][j] = matrix.get(startRow + i, startCol + j);
            }
        }

        return new DenseMatrix(subValues);
    }

}
