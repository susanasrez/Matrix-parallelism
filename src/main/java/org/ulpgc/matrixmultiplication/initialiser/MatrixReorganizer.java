package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;

public class MatrixReorganizer {

    public static Matrix reorganizeMatrix(Matrix matrix, int availableThreads) {
        int newSize = calculateNewSize(matrix.size(), availableThreads);
        int numAdded = newSize - matrix.size();
        int[] newsPartitions = calculateBlockSize(newSize, availableThreads);
        Matrix resizeMatrix = copyAndResize(newSize, matrix);
        Matrix[][] subPartitions = SubPartitioner.createSubPartitions(resizeMatrix, newsPartitions[1], newsPartitions[0]);
        return new PartitionMatrix(matrix.size(),subPartitions,newsPartitions[0],newsPartitions[1], numAdded);
    }

    private static int calculateNewSize(int size, int availableThreads) {
        int newSize = size+1;

        while(true){
            for(int i = availableThreads; i >= 2; i--){
                if (newSize % i == 0){
                    int nBlocks = i * i;
                    if (availableThreads >= nBlocks){
                        return newSize;
                    }
                }
            }
            newSize++;
        }
    }

    public static Matrix copyAndResize(int newSize, Matrix matrix) {
        int originalSize = matrix.size();
        double[][] copiedValues = new double[newSize][newSize];
        double[][] originalValues = ((DenseMatrix) matrix).values();

        for (int i = 0; i < originalSize; i++) {
            System.arraycopy(originalValues[i], 0, copiedValues[i], 0, originalSize);
        }

        return new DenseMatrix(copiedValues);
    }


    private static int[] calculateBlockSize(int size, int availableThreads) {
        int idealBlockSize = 1;
        int threads = 1;
        for (int i = size; i >= 1; i--) {
            if (size % i == 0) {
                int blocks = (size / i) * (size / i);
                if (blocks <= availableThreads && blocks != 1) {
                    idealBlockSize = i;
                    threads = blocks;
                }
            }
        }

        return new int[]{idealBlockSize,threads};
    }


}
