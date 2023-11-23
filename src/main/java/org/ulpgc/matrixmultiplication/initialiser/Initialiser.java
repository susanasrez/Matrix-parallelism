package org.ulpgc.matrixmultiplication.initialiser;

import org.ulpgc.matrixmultiplication.Matrix;

public interface Initialiser {
    Matrix calculatePartition(Matrix originalMatrix);
}
