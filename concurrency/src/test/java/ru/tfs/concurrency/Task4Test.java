package ru.tfs.concurrency;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.tfs.concurrency.task2.withoutDeadlock.Account;
import ru.tfs.concurrency.task2.withoutDeadlock.AccountThread;
import ru.tfs.concurrency.task4.ForkJoinQuickSort;
import ru.tfs.concurrency.task4.QuickSortThread;
import ru.tfs.concurrency.task4.RecursiveQuickSort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Task4Test extends TestCase {

    @Test
    public void testRecursive() {
        Random random = new Random();
        List<Comparable> actual = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            actual.add(random.nextDouble());
        }

        List<Comparable> expected = new ArrayList<>(actual);
        expected.sort(Comparator.naturalOrder());

        new RecursiveQuickSort(actual).sort();

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testThread() {
        Random random = new Random();
        List<Comparable> actual = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            actual.add(random.nextDouble());
        }

        List<Comparable> expected = new ArrayList<>(actual);
        expected.sort(Comparator.naturalOrder());

        new QuickSortThread(actual).sort();

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testForkJoin() {
        Random random = new Random();
        List<Comparable> actual = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            actual.add(random.nextDouble());
        }

        List<Comparable> expected = new ArrayList<>(actual);
        expected.sort(Comparator.naturalOrder());

        new ForkJoinQuickSort(actual).sort();

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

}
