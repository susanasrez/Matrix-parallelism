package org.ulpgc.matrixmultiplication.matrix;

import org.ulpgc.matrixmultiplication.Matrix;

public class PartitionMatrix implements Matrix {
    public int originalSize;
    public int blockSize;
    public int threads;
    public int numAdded;
    public Matrix[][] subPartitions;

    public PartitionMatrix(int originalSize, Matrix[][] subPartitions, int blockSize, int threads, int numAdded) {
        this.originalSize = originalSize;
        this.subPartitions = subPartitions;
        this.blockSize = blockSize;
        this.threads = threads;
        this.numAdded = numAdded;
    }

    @Override
    public int size() {
        return subPartitions.length;
    }

    @Override
    public double get(int i, int j) {
        return 0;
    }
}
