package ru.tfs.collections_generics;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.tfs.collections_generics.tasks1_2.Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task2Test extends TestCase {

    @Test
    public void test1() {
        List<Integer> veryLargeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            veryLargeList.add(i);
        }

        List<Integer> actual = new ArrayList<>();
        for (int i = 999999; i >= 999990; i--) {
            actual.add(i);
        }

        Assert.assertEquals(Loader.getMax10(veryLargeList), actual);
    }

}
