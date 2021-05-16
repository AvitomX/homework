package ru.tfs.collections_generics.tasks1_2;

import java.util.*;
import java.util.stream.Collectors;

public class Loader {
    public static void main(String[] args) {
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
        System.out.println(showWinner(competitors));

        List<Integer> veryLargeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            veryLargeList.add(random.nextInt());
        }
        System.out.println(getMax10(veryLargeList).toString());

    }

    public static String showWinner(List<String> competitors) {
       Map<String, Integer> map = new HashMap<>();
       Integer winValue = 0;
       String winner = "";

        for (String e: competitors) {
            String[] strings = e.split(" ");
            String key = strings[0];
            Integer value = Integer.valueOf(strings[1]);

            if (map.containsKey(key)){
                value = map.get(key) + value;
            }

            if (value > winValue) {
                winner = key;
                winValue = value;
            }
            map.put(key, value);
        }

        return winner;
    }

    public static List<Integer> getMax10(List<Integer> veryLargeList) {
        return veryLargeList.stream()
                .sorted(Comparator.reverseOrder())
                .limit(10)
                .collect(Collectors.toList());
    }

}