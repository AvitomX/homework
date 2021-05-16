package ru.tfs.concurrency.task1.multithread;

import java.util.concurrent.CountDownLatch;

public class RowMultiplyWorker implements Runnable {

    private final int[][] result;
    private int[][] matrix1;
    private int[][] matrix2;
    private final int row;
    private CountDownLatch latch;

    public RowMultiplyWorker(int[][] result, int[][] matrix1, int[][] matrix2, int row, CountDownLatch latch) {
        this.result = result;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.row = row;
        this.latch = latch;
    }

    @Override
    public void run() {

        for (int j = 0; j < matrix2[0].length; j++) {
            for (int k = 0; k < matrix1[row].length; k++) {
                result[row][j] += matrix1[row][k] * matrix2[k][j];
            }
        }
        latch.countDown();

    }

}
