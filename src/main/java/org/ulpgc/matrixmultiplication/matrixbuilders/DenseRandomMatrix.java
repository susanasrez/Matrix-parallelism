package org.ulpgc.matrixmultiplication.matrixbuilders;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.MatrixBuilder;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;

import java.util.Random;

public class DenseRandomMatrix implements MatrixBuilder {

    private final Random random = new Random();
    private double[][] values;

    private int size;

    @Override
    public void set(int i, int j, double value) {
        values[i][j] = value;
    }

    @Override
    public void setMatrix(Matrix c) {
        DenseMatrix denseMatrix = (DenseMatrix) c;
        this.values = denseMatrix.values();
        this.size = denseMatrix.size();
    }

    @Override
    public Matrix get() {
        return null;
    }

    public Matrix matrix(int size){
        this.size = size;
        values = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                values[i][j] = random.nextDouble();
            }
        }
        return new DenseMatrix(values);
    }
}
