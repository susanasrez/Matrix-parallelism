package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;

public class BlockSizeCalculator {

    private static int availableThreads;
    private static int size;
    private static Matrix matrix;

    public static Matrix calculatePartition(Matrix originalMatrix) {
        matrix=originalMatrix;
        size = originalMatrix.size() ;
        availableThreads = Runtime.getRuntime().availableProcessors();
        return calculate();
    }

    private static Matrix calculate() {
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

        if (threads == 1){
            return MatrixReorganizer.reorganizeMatrix(matrix,availableThreads);
        }

        return new PartitionMatrix(matrix,idealBlockSize,threads, false, 0);
    }

}
