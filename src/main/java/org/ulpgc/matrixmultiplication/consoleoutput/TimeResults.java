package org.ulpgc.matrixmultiplication.consoleoutput;

import org.ulpgc.matrixmultiplication.Matrix;

public class TimeResults {
    public Matrix characteristics;
    public long timeParallel;
    public long timeSequential;
    public boolean testParallel;
    public boolean testSequential;
    public boolean areResultsEquals;


    public void characteristics(Matrix characteristics) {
        this.characteristics = characteristics;
    }

    public void timeParallel(long timeParallel) {
        this.timeParallel = timeParallel;
    }

    public void timeSequential(long timeSequential) {
        this.timeSequential = timeSequential;
    }

    public void testParallel(boolean testParallel) {
        this.testParallel = testParallel;
    }

    public void testSequential(boolean testSequential) {
        this.testSequential = testSequential;
    }

    public void areResultsEquals(boolean areResultsEquals) {
        this.areResultsEquals = areResultsEquals;
    }

}
