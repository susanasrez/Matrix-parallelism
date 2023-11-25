package times;

import org.ulpgc.matrixmultiplication.Matrix;
import org.ulpgc.matrixmultiplication.matrixbuilders.DenseRandomMatrix;

public class Operators {

    public static Matrix operator(int n){
        DenseRandomMatrix denseRandom = new DenseRandomMatrix();
        return denseRandom.matrix(n);
    }
}
