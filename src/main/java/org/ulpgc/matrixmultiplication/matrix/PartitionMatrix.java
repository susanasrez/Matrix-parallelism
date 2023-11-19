package org.ulpgc.matrixmultiplication.matrix;

import org.ulpgc.matrixmultiplication.Matrix;

public class PartitionMatrix implements Matrix {
    public int blockSize;
    public int threads;

    public boolean hasRestructured;
    public int numAdded;
    public Matrix[][] subPartitions;

    public PartitionMatrix(Matrix[][] subPartitions, int blockSize, int threads, boolean hasRestructured, int numAdded) {
        this.subPartitions = subPartitions;
        this.blockSize = blockSize;
        this.threads = threads;
        this.hasRestructured = hasRestructured;
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
