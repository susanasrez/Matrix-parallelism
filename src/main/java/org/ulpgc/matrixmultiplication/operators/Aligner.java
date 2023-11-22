package org.ulpgc.matrixmultiplication.operators;


import org.ulpgc.matrixmultiplication.Matrix;

public class Aligner {

    public static Matrix[][] horizontalAlignmentInitial(Matrix[][] matrix){
        int dimension = matrix.length;
        Matrix[][] newOrder = new Matrix[dimension][dimension];
        //Cambiar lo de i =0

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(i == 0){
                    newOrder[i][j] = matrix[i][j];
                }
                else {
                    int newCol = ((j - i) >= 0) ? (j - i): (dimension+(j -i));
                    newOrder[i][newCol] = matrix[i][j];
                }
            }
        }
        return newOrder;
    }

    public static Matrix[][] verticalAlignmentInitial(Matrix[][] matrix){
        int dimension = matrix.length;
        Matrix[][] newOrder = new Matrix[dimension][dimension];

        for(int j = 0; j < dimension; j ++){
            for(int i = 0; i < dimension; i++){
                if(j == 0){
                    newOrder[i][j] = matrix[i][j];
                }
                else {
                    int newRow = ((i - j) >= 0) ? (i - j): (dimension+(i -j));
                    newOrder[newRow][j] = matrix[i][j];
                }
            }
        }

        return newOrder;
    }
    
    public static Matrix[][] horizontalAlign(Matrix[][] matrix){
        int dimension = matrix.length;
        Matrix[][] newOrder = new Matrix[dimension][dimension];

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                int newCol = ((j - (dimension-1)) >= 0) ? (j - (dimension-1)): (dimension+(j -(dimension-1)));
                newOrder[i][newCol] = matrix[i][j];
            }
        }
        return newOrder;
    }

    public static Matrix[][] verticalAlign(Matrix[][] matrix){
        int dimension = matrix.length;
        Matrix[][] newOrder = new Matrix[dimension][dimension];

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                int newRow = ((i - (dimension-1)) >= 0) ? (i - (dimension-1)): (dimension+(i -(dimension-1)));
                newOrder[newRow][j] = matrix[i][j];
            }
        }
        return newOrder;
    }


}
