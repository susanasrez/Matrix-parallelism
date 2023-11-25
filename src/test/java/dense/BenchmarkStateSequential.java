package dense;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.initialiser.BlockSizeCalculator;
import org.ulpgc.matrixmultiplication.initialiser.Initialiser;
import org.ulpgc.matrixmultiplication.matrixbuilders.DenseRandomMatrix;

public class BenchmarkStateSequential {

    @State(Scope.Thread)
    public static class Operands {
        @Param({"8", "16", "32", "64", "128", "256", "512", "1024", "2048", "4096", "8192", "16384", "32768", "65536",
                "131072", "262144", "524288", "1048576", "2097152", "4194304"})
        int n;

        public Matrix matrixA;
        DenseRandomMatrix denseRandomMatrix = new DenseRandomMatrix();

        @Setup
        public void setup() {
            matrixA = denseRandomMatrix.matrix(n);
        }

    }
}
