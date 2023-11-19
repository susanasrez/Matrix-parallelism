package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;


public class SubPartitioner {

    public static Matrix[][] createSubPartitions(PartitionMatrix partitionMatrix){
        int dimension = (int) Math.sqrt(partitionMatrix.threads);
        Matrix[][] subPartitions =new Matrix[dimension][dimension];

        int size = partitionMatrix.matrix.size();
        int blockSize = partitionMatrix.blockSize;

        for (int i = 0; i < size; i += blockSize) {
            for (int j = 0; j < size; j += blockSize) {
                int subSize = Math.min(blockSize, size - i);
                Matrix subPartition = getSubMatrix((DenseMatrix) partitionMatrix.matrix, i, j, subSize);
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
