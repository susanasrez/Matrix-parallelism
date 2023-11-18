package org.ulpgc.matrixmultiplication.matrix;

import org.ulpgc.matrixmultiplication.Matrix;

public class PartitionMatrix implements Matrix {
    public Matrix matrix;
    public int blockSize;
    public int threads;

    public boolean hasRestructured;
    public int numAdded;

    public PartitionMatrix(Matrix matrix, int blockSize, int threads, boolean hasRestructured, int numAdded) {
        this.matrix = matrix;
        this.blockSize = blockSize;
        this.threads = threads;
        this.hasRestructured = hasRestructured;
        this.numAdded = numAdded;
    }

    @Override
    public int size() {
        return matrix.size();
    }

    @Override
    public double get(int i, int j) {
        return 0;
    }
}
