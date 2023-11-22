package org.ulpgc.matrixmultiplication.checker;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.initialiser.MatrixRestorer;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;
import org.ulpgc.matrixmultiplication.matrixbuilders.DenseRandomMatrix;
import org.ulpgc.matrixmultiplication.operators.MatrixMultiplication;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.DenseMatrixMultiplication;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.TilesMatrixMultiplier;

import static org.ulpgc.matrixmultiplication.initialiser.BlockSizeCalculator.calculatePartition;

public class ShowResults {

    public static TimeResults timeResults = new TimeResults();

    public static TimeResults calculate(int n){
        DenseRandomMatrix denseRandomMatrix = new DenseRandomMatrix();
        DenseMatrix denseMatrix = (DenseMatrix) denseRandomMatrix.matrix(n);

        Matrix result_parallel = time_mult_tiles(denseMatrix);
        Matrix result_sequential = time_mult_dense(denseMatrix);

        testParallel(result_parallel, result_sequential, denseMatrix);

        return timeResults;
    }

    public static Matrix time_mult_tiles(DenseMatrix denseMatrix) {
        PartitionMatrix partitionMatrix = (PartitionMatrix) calculatePartition(denseMatrix);
        timeResults.characteristics(partitionMatrix);

        TilesMatrixMultiplier tilesMatrixMultiplier = new TilesMatrixMultiplier();
        long startTime = System.currentTimeMillis();
        PartitionMatrix result_tiles = tilesMatrixMultiplier.tilesMultiplication(partitionMatrix, partitionMatrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        timeResults.timeParallel(executionTime);

        return MatrixRestorer.resetMatrix(result_tiles);
    }

    public static Matrix time_mult_dense(DenseMatrix denseMatrix){
        MatrixMultiplication denseMatrixMult = new DenseMatrixMultiplication();
        long startTime = System.currentTimeMillis();
        Matrix result_sequential = denseMatrixMult.multiply(denseMatrix, denseMatrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        timeResults.timeSequential(executionTime);

        return result_sequential;
    }

    public static void testParallel(Matrix result_parallel, Matrix result_sequential, Matrix operand){
        boolean testParallel = Checker.test(operand, operand, result_parallel);
        boolean testSequential = Checker.test(operand, operand, result_sequential);
        boolean matricesEqual = Checker.areMatricesEqual(result_parallel, result_sequential);
        timeResults.testParallel(testParallel);
        timeResults.testSequential(testSequential);
        timeResults.areResultsEquals(matricesEqual);
    }

}
