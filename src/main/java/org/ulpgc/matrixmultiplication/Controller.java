package org.ulpgc.matrixmultiplication;

import org.ulpgc.matrixmultiplication.checker.Viewer;
import org.ulpgc.matrixmultiplication.matrix.DenseMatrix;
import org.ulpgc.matrixmultiplication.matrix.PartitionMatrix;
import org.ulpgc.matrixmultiplication.operators.TilesMatrixMultiplier;

import static org.ulpgc.matrixmultiplication.initialiser.BlockSizeCalculator.calculatePartition;

public class Controller {

    public Controller(){

        double[][] matrixValues = {
                {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
                {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0},
                {3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0},
                {4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0},
                {5.0 ,5.0 ,5.0 ,5.0 ,5.0 ,5.0 ,5.0},
                {6.0 ,6.0 ,6.0 ,6.0 ,6.0 ,6.0 ,6.0},
                {7.0, 7.0, 7.0, 7.0, 7.0, 7.0, 7.0}
        };

        double[][] matrixValues2 = {
                {1.0, 1.0, 1.0},
                {1.0, 1.0, 1.0},
                {2.0, 2.0, 2.0}
        };

        double[][] matrixValues3 = {
                {1.0, 2.0, 1.0},
                {1.0, 3.0, 1.0},
                {2.0, 2.0, 4.0}
        };

        DenseMatrix denseMatrix1 = new DenseMatrix(matrixValues2);
        DenseMatrix denseMatrix2 = new DenseMatrix(matrixValues3);
        //DenseMatrix denseMatrix = (DenseMatrix) new DenseRandomMatrix().matrix(i);
        PartitionMatrix partitionMatrix = (PartitionMatrix) calculatePartition(denseMatrix1);
        PartitionMatrix partitionMatrix2 = (PartitionMatrix) calculatePartition(denseMatrix2);
        Viewer.showDense(denseMatrix1);
        System.out.println("2");
        Viewer.showDense(denseMatrix2);
        System.out.println("bloques = " + partitionMatrix.blockSize);
        System.out.println("hilos = " + partitionMatrix.threads);
        System.out.println("size = "+ partitionMatrix.size());
        //Viewer.showDense(partitionMatrix.matrix);

        System.out.println("Realizando la multiplicación...");

        PartitionMatrix result = (PartitionMatrix) new TilesMatrixMultiplier().multiply(partitionMatrix, partitionMatrix2);
        Viewer.showDense(result.matrix);
    }
}
