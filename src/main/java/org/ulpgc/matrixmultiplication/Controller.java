package org.ulpgc.matrixmultiplication;

import org.ulpgc.matrixmultiplication.checker.Viewer;
import org.ulpgc.matrixmultiplication.initialiser.Aligner;
import org.ulpgc.matrixmultiplication.initialiser.SubPartitioner;
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
                {1.0, 2.0, 3.0, 4.0},
                {5.0, 6.0, 7.0, 8.0},
                {9.0, 10.0, 11.0, 12.0},
                {13.0, 14.0, 15.0, 16.0 }
        };

        DenseMatrix denseMatrix2 = new DenseMatrix(matrixValues3);

        PartitionMatrix partitionMatrix = (PartitionMatrix) calculatePartition(denseMatrix2);
        Viewer.showDense(denseMatrix2);
        System.out.println("bloques = " + partitionMatrix.blockSize);
        System.out.println("hilos = " + partitionMatrix.threads);
        System.out.println("size = "+ partitionMatrix.size());

        Matrix[][] subpartitions = SubPartitioner.createSubPartitions(partitionMatrix);
        Matrix[][] order = Aligner.verticalAlign(subpartitions);

        /*for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < order[i].length; j++) {
                System.out.println("Partición [" + i + "][" + j + "]");
                Viewer.showDense(order[i][j]);
                System.out.println("");
            }
        }*/
        TilesMatrixMultiplier tilesMatrixMultiplier = new TilesMatrixMultiplier();
        Matrix[][] xd = tilesMatrixMultiplier.tilesMultiplication(subpartitions, subpartitions);
        for (int i = 0; i < xd.length; i++) {
            for (int j = 0; j < xd[i].length; j++) {
                System.out.println("Partición [" + i + "][" + j + "]");
                Viewer.showDense(xd[i][j]);
                System.out.println(" ");
            }
        }
    }
}
