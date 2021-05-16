package ru.tfs.concurrency.task2.deadlock;

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
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    if (accountFrom.takeOffMoney(money)) {
                        accountTo.addMoney(money);
                        System.out.println(accountFrom.toString() + "->" + accountTo.toString() + " amount: " + money);
                    }
                }
            }
        }
    }
}
