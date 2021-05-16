package ru.tfs.concurrency.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loader {
    public static void main(String[] args) {
        Random random = new Random();
        List<Comparable> firstList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            firstList.add(random.nextDouble());
        }
        List<Comparable> secondList = new ArrayList<>(firstList);
        List<Comparable> thirdList = new ArrayList<>(firstList);

        RecursiveQuickSort recursiveQuickSort = new RecursiveQuickSort(firstList);
        long start = System.currentTimeMillis();
        recursiveQuickSort.sort();
        long finish = System.currentTimeMillis();
        System.out.println(finish - start + " ms - RecursiveQuickSort");

        QuickSortThread quickSortThread = new QuickSortThread(secondList);
        start = System.currentTimeMillis();
        quickSortThread.sort();
        finish = System.currentTimeMillis();
        System.out.println(finish - start + " ms - QuickSortThread");

        ForkJoinQuickSort forkJoinQuickSort = new ForkJoinQuickSort(thirdList);
        start = System.currentTimeMillis();
        forkJoinQuickSort.sort();
        finish = System.currentTimeMillis();
        System.out.println(finish - start + " ms - ForkJoinQuickSort");
    }
}
