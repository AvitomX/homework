package ru.tfs.concurrency.task4;

import java.util.List;

public class QuickSortThread implements QuickSort {
    private List<Comparable> values;
    private int low;
    private int high;

    public QuickSortThread(List<Comparable> values) {
        this.values = values;
        this.low = 0;
        this.high = values.size() - 1;
    }

    @Override
    public void sort() {
        Thread thread = new Thread(() -> {
            try {
                quickSort(low, high);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void quickSort(int begin, int end) throws InterruptedException {
        if (begin < end) {
            int partIndex = partition(begin, end);

            Thread leftThread = new Thread(() -> {
                try {
                    quickSort(begin, partIndex - 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            leftThread.start();
            leftThread.join();

            Thread rightThread = new Thread(() -> {
                try {
                    quickSort(partIndex + 1, end);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            rightThread.start();
            rightThread.join();
        }
    }

    private int partition(int begin, int end) {
        Comparable pivot = this.values.get(end);
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (pivot.compareTo(this.values.get(j)) > 0) {
                i++;

                Comparable temp = this.values.get(i);
                this.values.set(i, this.values.get(j));
                this.values.set(j, temp);
            }
        }

        Comparable temp = this.values.get(i + 1);
        this.values.set(i + 1, this.values.get(end));
        this.values.set(end, temp);

        return i + 1;
    }
}
