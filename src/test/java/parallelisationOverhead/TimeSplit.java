package parallelisationOverhead;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.initialiser.BlockSizeCalculator;
import org.ulpgc.matrixmultiplication.initialiser.Initialiser;
import org.ulpgc.matrixmultiplication.matrix.PartitionedMatrix;
import times.Operators;

import java.util.Arrays;
import java.util.List;

public class TimeSplit {
    private static Initialiser initialiser = new BlockSizeCalculator();

    public static void main(String[] args) {
        List<Integer> dimensions = Arrays.asList(8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096,
                8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304);
        for (Integer n : dimensions){
            System.out.println();
            Matrix matrixA = Operators.operator(4096);
            timerSplit(matrixA);
        }
    }

    public static void timerSplit(Matrix matrix){
        long startTime = System.currentTimeMillis();
        initialiser.calculatePartition(matrix);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Parallel split: "+ executionTime+ " milliseconds.");

    }
}
