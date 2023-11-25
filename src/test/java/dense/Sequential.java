package dense;

import org.openjdk.jmh.annotations.*;
import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.operators.matrixmultiplication.DenseMatrixMultiplication;

import java.util.concurrent.TimeUnit;

@Fork(value = 1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1)
//Cambiar a 3
@Measurement(iterations = 2)

public class Sequential {
    @Benchmark
    public void multiplication(BenchmarkStateSequential.Operands operands) {
        Matrix result = new DenseMatrixMultiplication().multiply(operands.matrixA, operands.matrixA);
    }
}
