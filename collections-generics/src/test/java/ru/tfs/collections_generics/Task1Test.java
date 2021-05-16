package ru.tfs.collections_generics;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.tfs.collections_generics.tasks1_2.Loader;

import java.util.ArrayList;
import java.util.List;

public class Task1Test extends TestCase {

    @Test
    public void test1(){
        List<String> competitors = new ArrayList<>();
        competitors.add("Ivan 5");
        competitors.add("Petr 3");
        competitors.add("Alex 10");
        competitors.add("Petr 8");
        competitors.add("Ivan 6");
        competitors.add("Alex 5");
        competitors.add("Ivan 1");
        competitors.add("Petr 5");
        competitors.add("Alex 1");

        String actual = Loader.showWinner(competitors);
        Assert.assertEquals("Petr", actual);
    }

    @Test
    public void test2(){
        List<String> competitors = new ArrayList<>();
        competitors.add("Ivan 5");
        competitors.add("Petr 3");
        competitors.add("Alex 10");
        competitors.add("Petr 8");
        competitors.add("Ivan 6");
        competitors.add("Alex 5");
        competitors.add("Ivan 1");
        competitors.add("Alex 1");
        competitors.add("Petr 5");

        String actual = Loader.showWinner(competitors);
        Assert.assertEquals("Alex", actual);
    }
}
