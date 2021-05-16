package ru.tfs.concurrency.task2.withoutDeadlock;

public class AccountThread implements Runnable {
    private final Account accountFrom;
    private final Account accountTo;
    private final int money;

    public AccountThread(Account accountFrom, Account accountTo, int money) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4000; i++) {
            boolean lockOn = accountFrom.compareTo(accountTo) > 0;
            Account firstLock = lockOn ? accountFrom : accountTo;
            Account secondLock = lockOn ? accountTo : accountFrom;
            synchronized (firstLock) {
                synchronized (secondLock) {
                    if (accountFrom.takeOffMoney(money)) {
                        accountTo.addMoney(money);
                    }
                }
            }
        }
    }
}
