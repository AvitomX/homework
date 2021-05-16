package ru.tfs.concurrency.task4;

import java.util.List;

public class RecursiveQuickSort implements QuickSort {
    private List<Comparable> values;
    private int low;
    private int high;

    public RecursiveQuickSort(List<Comparable> values) {
        this.values = values;
        this.low = 0;
        this.high = values.size() - 1;
    }

    @Override
    public void sort() {
        quickSort(low, high);
    }

    private void quickSort(int begin, int end) {
        if (begin < end) {
            int partIndex = partition(begin, end);

            quickSort(begin, partIndex - 1);
            quickSort(partIndex + 1, end);
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
