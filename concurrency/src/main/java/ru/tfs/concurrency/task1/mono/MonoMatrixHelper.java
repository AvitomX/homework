package ru.tfs.concurrency.task1.mono;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MonoMatrixHelper {

    public static int[][] monoMulti(int[][] matrix1, int[][] matrix2) {
        int resMatrixRow = matrix1.length;
        int resMatrixCol = matrix2[0].length;

        int[][] resMatrix = new int[resMatrixRow][resMatrixCol];

        for (int i = 0; i < resMatrixRow; i++) {
            for (int j = 0; j < resMatrixCol; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    resMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return resMatrix;
    }

    public static int[][] readMatrix(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<String> lines = new ArrayList<>();

            while (reader.ready()) {
                lines.add(reader.readLine());
            }

            int cols = lines.get(0).split(" ").length;
            int rows = lines.size();

            int[][] matrix = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    String[] line = lines.get(i).split(" ");
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }

            return matrix;
        }
    }

    public static void writeMatrix(int[][] matrix, String path) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8")) {

            for (int i = 0; i < matrix.length; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < matrix[0].length; j++) {
                    stringBuilder.append(matrix[i][j]).append(" ");
                }
                stringBuilder.append(System.lineSeparator());
                writer.write(stringBuilder.toString());
            }
        }
    }

}
