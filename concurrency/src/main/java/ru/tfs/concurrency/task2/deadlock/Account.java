package ru.tfs.concurrency.task2.deadlock;

public class Account implements Comparable<Account> {
    private String name;
    private int cacheBalance;

    public Account(int cacheBalance) {
        this.cacheBalance = cacheBalance;
    }

    public Account(String name, int cacheBalance) {
        this.name = name;
        this.cacheBalance = cacheBalance;
    }

    public void addMoney(int money) {
        this.cacheBalance += money;
    }

    public boolean takeOffMoney(int money) {
        if (this.cacheBalance < money) {
            return false;
        }

        this.cacheBalance -= money;
        return true;
    }

    public int getCacheBalance() {
        return cacheBalance;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Account account) {
        return this.getName().compareTo(account.getName());
    }
}
