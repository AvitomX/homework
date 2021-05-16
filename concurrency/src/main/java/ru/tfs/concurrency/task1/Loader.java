package ru.tfs.concurrency.task1;

import ru.tfs.concurrency.task1.mono.MonoMatrixHelper;
import ru.tfs.concurrency.task1.multithread.ParallelThreadsCreator;

import java.io.IOException;

import static ru.tfs.concurrency.task1.mono.MonoMatrixHelper.readMatrix;
import static ru.tfs.concurrency.task1.mono.MonoMatrixHelper.writeMatrix;

public class Loader {
    public static void main(String[] args) throws IOException, InterruptedException {
        String dirPath = System.getProperty("user.dir") + "/resources/";
        String matrixPath1 = dirPath + "1.txt";
        String matrixPath2 = dirPath + "2.txt";
        String monoResPath = dirPath + "res.txt";
        String multiResPath = dirPath + "multiRes.txt";

        int matrix1[][] = readMatrix(matrixPath1);
        int matrix2[][] = readMatrix(matrixPath2);

        if (matrix1[0].length != matrix2.length) {
            System.err.println("Matrices can't be multiplied");
            throw new RuntimeException();
        }

        int res[][] = MonoMatrixHelper.monoMulti(matrix1, matrix2);
        writeMatrix(res, monoResPath);

        int[][] multiResult = new int[matrix1.length][matrix2[0].length];
        ParallelThreadsCreator.multiply(matrix1, matrix2, multiResult);
        writeMatrix(multiResult, multiResPath);
    }
}
