package times;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.checker.Checker;
import org.ulpgc.matrixmultiplication.checker.MatrixMultiplicationChecker;
import org.ulpgc.matrixmultiplication.initialiser.BlockSizeCalculator;
import org.ulpgc.matrixmultiplication.initialiser.Initialiser;
import org.ulpgc.matrixmultiplication.initialiser.MatrixRestorer;
import org.ulpgc.matrixmultiplication.matrix.PartitionedMatrix;
import org.ulpgc.matrixmultiplication.operators.MatrixMultiplication;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.DenseMatrixMultiplication;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.TilesMatrixMultiplier;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.TilesMatrixMultiplierNoAlign;

import java.util.Arrays;
import java.util.List;

public class Timer {

    private static final MatrixMultiplicationChecker checker = new Checker();
    private static Matrix result_tiles;
    private static Matrix result_tiles_noalign;
    private static Matrix result_sequential;
    private static final Initialiser initialiser = new BlockSizeCalculator();

    public static void main(String[] args) throws InterruptedException {
        List<Integer> dimensions = Arrays.asList(8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096,
                8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304);

        //for (Integer n : dimensions) {
            Matrix matrixA = Operators.operator(8);
            System.out.println("N = " + 8);
            timerParallel(matrixA);
            timerParallelNoAlign(matrixA);
            timerSequential(matrixA);
            System.out.println("");
            System.out.println("");
            boolean testResultEqualsParallel = checker.areMatricesEqual(result_tiles, result_tiles_noalign);
            boolean testResultEqualsSequential =  checker.areMatricesEqual(result_tiles, result_sequential);
            System.out.println("Test parallel: " +testResultEqualsParallel);
            System.out.println("Test sequential: " +testResultEqualsSequential);
        //}

    }

    public static void timerParallel(Matrix matrix){
        PartitionedMatrix partitionMatrix = (PartitionedMatrix) initialiser.calculatePartition(matrix);
        MatrixMultiplication tilesMatrixMultiplier = new TilesMatrixMultiplier();

        long startTime = System.currentTimeMillis();
        result_tiles = ((TilesMatrixMultiplier) tilesMatrixMultiplier).tilesMultiplication(partitionMatrix, partitionMatrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        result_tiles = MatrixRestorer.resetMatrix(result_tiles);
        boolean testParallel = checker.test(matrix, matrix, result_tiles);

        System.out.println("Parallel multiplication: "+ executionTime+ " milliseconds.");
        System.out.println("Test it's: "+testParallel);
    }

    public static void timerParallelNoAlign(Matrix matrix) throws InterruptedException {
        PartitionedMatrix partitionMatrix = (PartitionedMatrix) initialiser.calculatePartition(matrix);
        TilesMatrixMultiplierNoAlign tilesMatrixMultiplierNoAlign = new TilesMatrixMultiplierNoAlign();

        long startTime = System.currentTimeMillis();
        result_tiles_noalign = tilesMatrixMultiplierNoAlign.tilesMultiplication(partitionMatrix, partitionMatrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        result_tiles_noalign = MatrixRestorer.resetMatrix(result_tiles_noalign);
        boolean testParallel = checker.test(matrix, matrix, result_tiles_noalign);

        System.out.println("Parallel multiplication without align: "+ executionTime+ " milliseconds.");
        System.out.println("Test it's: "+testParallel);
    }

    public static void timerSequential(Matrix matrix){
        MatrixMultiplication denseMatrixMult = new DenseMatrixMultiplication();

        long startTime = System.currentTimeMillis();
        result_sequential = denseMatrixMult.multiply(matrix, matrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        boolean testSequential = checker.test(matrix, matrix, result_sequential);

        System.out.println("Sequential multiplication: "+ executionTime+ " milliseconds.");
        System.out.println("Test it's: "+testSequential);
    }

}
