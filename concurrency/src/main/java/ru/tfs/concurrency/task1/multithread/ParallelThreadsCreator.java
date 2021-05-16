package ru.tfs.concurrency.task1.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelThreadsCreator {

    public static void multiply(int[][] matrix1, int[][] matrix2, int[][] result) throws InterruptedException {
        int rows = matrix1.length;
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(cores + 1);
        CountDownLatch latch = new CountDownLatch(rows);

        for (int i = 0; i < rows; i++) {
            RowMultiplyWorker task = new RowMultiplyWorker(result, matrix1, matrix2, i, latch);
            executorService.execute(task);
        }

        latch.await();
        executorService.shutdown();
    }

}
