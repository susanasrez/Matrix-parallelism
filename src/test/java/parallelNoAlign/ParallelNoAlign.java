package parallelNoAlign;

import org.openjdk.jmh.annotations.*;
import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.TilesMatrixMultiplierNoAlign;

import java.util.concurrent.TimeUnit;

@Fork(value = 1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1)
//Cambiar por 3
@Measurement(iterations = 2)

public class ParallelNoAlign {

    @Benchmark
    public void multiplication(BenchmarkStateNoAlign.Operands operands) {
        Matrix result = new TilesMatrixMultiplierNoAlign().multiply(operands.matrixA, operands.matrixA);
    }
}
