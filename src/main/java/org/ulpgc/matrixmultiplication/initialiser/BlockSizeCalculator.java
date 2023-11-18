package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;

public class BlockSizeCalculator {

    private static int availableThreads;
    private static int size;
    public static Matrix newMatrix;

    public static PartitionMatrix calculatePartition(Matrix matrix) {
        newMatrix=matrix;
        size = matrix.size();
        availableThreads = Runtime.getRuntime().availableProcessors();
        return calculate();
    }

    private static PartitionMatrix calculate() {
        int idealBlockSize = 1;
        int threads = 1;

        for(int i = 2; i <= availableThreads; i++){
            if (size % i == 0){
                if (availableThreads >= (size / i)){
                    idealBlockSize = i;
                    threads = size /i;
                    break;
                }
            }
        }

        if (idealBlockSize == 1 || threads == 1){
            return MatrixReorganizer.reorganizeMatrix(newMatrix,availableThreads);
        }
        return new PartitionMatrix(newMatrix,idealBlockSize,threads, false, 0);
    }

}
