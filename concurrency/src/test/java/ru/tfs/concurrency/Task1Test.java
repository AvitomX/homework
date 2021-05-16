package ru.tfs.concurrency;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.tfs.concurrency.task1.mono.MonoMatrixHelper;
import ru.tfs.concurrency.task1.multithread.ParallelThreadsCreator;

public class Task1Test extends TestCase {
    int expected[][] = {
            {6, 9, 31},
            {-2, -1, 1},
            {10, 9, -9}
    };

    int m1[][] = {
            {3, 2, 3, 5},
            {-1, 0, 3, -1},
            {5, 7, 8, -5}
    };

    int m2[][] = {
            {2, 0, 0},
            {0, 2, 0},
            {0, 0, 2},
            {0, 1, 5}
    };

    @Test
    public void test1() {
        Assert.assertArrayEquals(expected, MonoMatrixHelper.monoMulti(m1, m2));
    }

    @Test
    public void test2() {
        int[][] actual = new int[m1.length][m2[0].length];
        try {
            ParallelThreadsCreator.multiply(m1, m2, actual);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertArrayEquals(expected, actual);
    }
}
