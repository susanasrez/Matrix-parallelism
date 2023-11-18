package org.ulpgc.matrixmultiplication;

import org.ulpgc.matrixmultiplication.checker.Viewer;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;
import org.ulpgc.matrixmultiplication.matrixbuilders.DenseRandomMatrix;

import static org.ulpgc.matrixmultiplication.initialiser.BlockSizeCalculator.calculatePartition;

public class Controller {

    public Controller(){
        Matrix denseMatrix = new DenseRandomMatrix().matrix(22);
        PartitionMatrix partitionMatrix = calculatePartition(denseMatrix);
        Viewer.showDense(denseMatrix);
    }
}
