package parallel;

import org.openjdk.jmh.annotations.*;
import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.TilesMatrixMultiplier;

import java.util.concurrent.TimeUnit;

@Fork(value = 1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1)
//Cambiar a 3
@Measurement(iterations = 2)

public class ParallelAlign {

    @Benchmark
    public void multiplication(BenchmarkStateAlign.Operands operands) {
        Matrix result = new TilesMatrixMultiplier().multiply(operands.matrixA, operands.matrixA);
    }

}
