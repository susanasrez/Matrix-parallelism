package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionedMatrix;

public class BlockSizeCalculator implements Initialiser{

    private static int availableThreads;
    private static int size;
    private static Matrix matrix;

    @Override
    public Matrix calculatePartition(Matrix originalMatrix) {
        matrix=originalMatrix;
        size = originalMatrix.size() ;
        availableThreads = Runtime.getRuntime().availableProcessors();
        return calculate();
    }

    private static Matrix calculate() {
        int idealBlockSize = 1;
        int threads = 1;

        for (int i = 1; i <= size; i++) {
            if (size % i == 0) {
                int blocks = (size / i) * (size / i);
                if (blocks <= availableThreads && blocks != 1) {
                    idealBlockSize = i;
                    threads = blocks;
                    break;
                }
            }
        }

        if (threads == 1){
            return MatrixReorganizer.reorganizeMatrix(matrix,availableThreads);
        }

        Matrix[][] subPartition = SubPartitioner.createSubPartitions(matrix, threads, idealBlockSize);

        return new PartitionedMatrix(matrix.size(), subPartition,idealBlockSize,threads, 0);
    }

}
