package org.ulpgc.matrixmultiplication.consoleoutput;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.MatrixConsoleUtil;
import org.ulpgc.matrixmultiplication.checker.Checker;
import org.ulpgc.matrixmultiplication.checker.MatrixMultiplicationChecker;
import org.ulpgc.matrixmultiplication.initialiser.BlockSizeCalculator;
import org.ulpgc.matrixmultiplication.initialiser.Initialiser;
import org.ulpgc.matrixmultiplication.initialiser.MatrixRestorer;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionedMatrix;
import org.ulpgc.matrixmultiplication.matrixbuilders.DenseRandomMatrix;
import org.ulpgc.matrixmultiplication.operators.MatrixMultiplication;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.DenseMatrixMultiplication;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.TilesMatrixMultiplier;


public class MatrixMultiplicationResultPresenter implements MatrixConsoleUtil {

    public static TimeResults timeResults = new TimeResults();
    MatrixMultiplicationChecker checker = new Checker();
    Initialiser initialiser = new BlockSizeCalculator();

    @Override
    public TimeResults setUp(int n){
        DenseRandomMatrix denseRandomMatrix = new DenseRandomMatrix();
        DenseMatrix denseMatrix = (DenseMatrix) denseRandomMatrix.matrix(n);

        Matrix result_parallel = time_mult_tiles(denseMatrix);
        Matrix result_sequential = time_mult_dense(denseMatrix);

        knowAttributes(result_parallel, result_sequential, denseMatrix);

        return timeResults;
    }

    @Override
    public Matrix time_mult_tiles(DenseMatrix denseMatrix) {
        PartitionedMatrix partitionMatrix = (PartitionedMatrix) initialiser.calculatePartition(denseMatrix);
        timeResults.characteristics(partitionMatrix);

        MatrixMultiplication tilesMatrixMultiplier = new TilesMatrixMultiplier();
        long startTime = System.currentTimeMillis();
        Matrix result_tiles = ((TilesMatrixMultiplier) tilesMatrixMultiplier).tilesMultiplication(partitionMatrix, partitionMatrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        timeResults.timeParallel(executionTime);

        return MatrixRestorer.resetMatrix(result_tiles);
    }

    @Override
    public Matrix time_mult_dense(DenseMatrix denseMatrix){
        MatrixMultiplication denseMatrixMult = new DenseMatrixMultiplication();
        long startTime = System.currentTimeMillis();
        Matrix result_sequential = denseMatrixMult.multiply(denseMatrix, denseMatrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        timeResults.timeSequential(executionTime);

        return result_sequential;
    }

    public void knowAttributes(Matrix result_parallel, Matrix result_sequential, Matrix operand){
        boolean testParallel = checker.test(operand, operand, result_parallel);
        boolean testSequential = checker.test(operand, operand, result_sequential);
        boolean matricesEqual = checker.areMatricesEqual(result_parallel, result_sequential);
        timeResults.testParallel(testParallel);
        timeResults.testSequential(testSequential);
        timeResults.areResultsEquals(matricesEqual);
    }

}
