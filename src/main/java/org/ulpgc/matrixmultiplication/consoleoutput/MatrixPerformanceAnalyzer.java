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


public class MatrixPerformanceAnalyzer implements MatrixConsoleUtil {

    MatrixMultiplicationChecker checker = new Checker();
    Initialiser initialiser = new BlockSizeCalculator();
    @Override
    public TimeResults setUp(int n){
        DenseRandomMatrix denseRandomMatrix = new DenseRandomMatrix();
        DenseMatrix denseMatrix = (DenseMatrix) denseRandomMatrix.matrix(n);

        Matrix result_parallel = time_mult_tiles(denseMatrix);
        Matrix result_sequential = time_mult_dense(denseMatrix);

        boolean good = checker.areMatricesEqual(result_parallel, result_sequential);
        System.out.println();
        System.out.println("Test = " + good);
        return null;
    }

    @Override
    public Matrix time_mult_tiles(DenseMatrix denseMatrix){
        PartitionedMatrix partitionMatrix = (PartitionedMatrix) initialiser.calculatePartition(denseMatrix);
        knowAttributes(partitionMatrix);

        MatrixMultiplication tilesMatrixMultiplier = new TilesMatrixMultiplier();
        long startTime = System.currentTimeMillis();
        Matrix result_tiles = ((TilesMatrixMultiplier) tilesMatrixMultiplier).tilesMultiplication(partitionMatrix, partitionMatrix);
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("Parallel runtime: " + executionTime + " milliseconds");

        return MatrixRestorer.resetMatrix(result_tiles);
    }

    @Override
    public Matrix time_mult_dense(DenseMatrix denseMatrix){
        MatrixMultiplication denseMatrixMult = new DenseMatrixMultiplication();
        long startTime2 = System.currentTimeMillis();
        Matrix result_sequential = denseMatrixMult.multiply(denseMatrix, denseMatrix);
        long endTime2 = System.currentTimeMillis();
        long executionTime2 = endTime2 - startTime2;

        System.out.println("Sequential Runtime: " + executionTime2 + " milliseconds");
        return result_sequential;
    }

    public void knowAttributes(PartitionedMatrix partitionMatrix){System.out.println("Original size = " + partitionMatrix.originalSize);
        System.out.println("Block size = " + partitionMatrix.blockSize);
        System.out.println("Number of threads required = " + partitionMatrix.threads);
        System.out.println("Number of rows/columns added = " + partitionMatrix.numAdded);
        System.out.println("Subpartition Array Length = " + partitionMatrix.subPartitions.length );
        int newSize = partitionMatrix.originalSize + partitionMatrix.numAdded;
        System.out.println("New Size = "+ newSize);
        System.out.println();
    }

}
