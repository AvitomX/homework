package ru.tfs.concurrency.task2.withoutDeadlock;

import java.util.UUID;

public class Account implements Comparable<Account> {
    private UUID uuid;
    private int cacheBalance;

    public Account(int cacheBalance) {
        this.uuid = UUID.randomUUID();
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

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return this.uuid.toString();
    }

    @Override
    public int compareTo(Account account) {
        return this.getUuid().compareTo(account.getUuid());
    }
}
