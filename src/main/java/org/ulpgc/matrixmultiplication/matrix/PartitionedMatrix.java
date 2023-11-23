package org.ulpgc.matrixmultiplication.matrix;

import org.ulpgc.matrixmultiplication.Matrix;

public class PartitionedMatrix implements Matrix {
    public int originalSize;
    public int blockSize;
    public int threads;
    public int numAdded;
    public Matrix[][] subPartitions;

    public PartitionedMatrix(int originalSize, Matrix[][] subPartitions, int blockSize, int threads, int numAdded) {
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
