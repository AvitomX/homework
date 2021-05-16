package ru.tfs.concurrency.task3;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class Leg implements Runnable {
    private final String name;
    private Semaphore inLock;
    private Semaphore outLock;

    public Leg(String name, Semaphore inLock, Semaphore outLock) {
        this.name = name;
        this.inLock = inLock;
        this.outLock = outLock;
    }

    @Override
    public void run() {

        while (true) {
            try {
                inLock.acquire();
                System.out.println(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                outLock.release();
            }
        }

    }

    public static void main(String[] args) {
        Semaphore first = new Semaphore(1);
        Semaphore second = new Semaphore(0);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(new Leg("right", first, second)),
                CompletableFuture.runAsync(new Leg("left", second, first))
        ).join();
    }
}