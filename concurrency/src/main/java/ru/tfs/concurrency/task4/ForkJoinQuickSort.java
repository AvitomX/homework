package ru.tfs.concurrency.task4;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinQuickSort implements QuickSort {
    private ForkJoinPool pool;
    private List<Comparable> values;
    private int low;
    private int high;

    public ForkJoinQuickSort(List<Comparable> values) {
        this.values = values;
        this.low = 0;
        this.high = values.size() - 1;
    }

    @Override
    public void sort() {
        pool = new ForkJoinPool();
        RecursiveTask task = new RecursiveTask(values, low, high);
        pool.invoke(task);
    }

    class RecursiveTask extends RecursiveAction {
        private List<Comparable> values;
        private int begin;
        private int end;

        public RecursiveTask(List<Comparable> values, int begin, int end) {
            this.values = values;
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (begin < end) {
                int partIndex = partition(values, begin, end);

                invokeAll(new RecursiveTask(values, begin, partIndex - 1),
                        new RecursiveTask(values, partIndex + 1, end));
            }
        }

        private int partition(List<Comparable> values, int begin, int end) {
            Comparable pivot = values.get(end);
            int i = begin - 1;

            for (int j = begin; j < end; j++) {
                if (pivot.compareTo(values.get(j)) > 0) {
                    i++;

                    Comparable temp = values.get(i);
                    values.set(i, values.get(j));
                    values.set(j, temp);
                }
            }

            Comparable temp = values.get(i + 1);
            values.set(i + 1, values.get(end));
            values.set(end, temp);

            return i + 1;
        }
    }

}
