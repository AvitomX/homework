package ru.tfs.concurrency;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.tfs.concurrency.task2.withoutDeadlock.Account;
import ru.tfs.concurrency.task2.withoutDeadlock.AccountThread;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Task2Test extends TestCase {

    @Test
    public void test() {
        Account firstAccount = new Account(100_000);
        Account secondAccount = new Account(100_000);

        int expected = firstAccount.getCacheBalance() + secondAccount.getCacheBalance();

        AccountThread firstThread = new AccountThread(firstAccount, secondAccount, 100);
        AccountThread secondThread = new AccountThread(secondAccount, firstAccount, 100);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(firstThread),
                CompletableFuture.runAsync(secondThread)
        ).join();

        int actual = firstAccount.getCacheBalance() + secondAccount.getCacheBalance();

        Assert.assertEquals(expected, actual);
    }

}
