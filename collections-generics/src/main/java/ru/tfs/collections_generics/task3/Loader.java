package ru.tfs.collections_generics.task3;

import java.util.*;
import java.util.stream.Collectors;

public class Loader {
    public static void main(String[] args) {
        Random random = new Random();
        List<User> users = new ArrayList<>();

        User newUser = new User(1, "Alex", 25);
        newUser.addPhone("9253334455", PhoneType.MOBILE);
        newUser.addPhone("9254445566", PhoneType.MOBILE);
        newUser.addPhone("4958889900", PhoneType.LANDLINE);
        users.add(newUser);

        newUser = new User(2, "Maks", 68);
        newUser.addPhone("9255556677", PhoneType.MOBILE);
        newUser.addPhone("4950009966", PhoneType.LANDLINE);
        users.add(newUser);

        newUser = new User(3, "Vasya", 68);
        newUser.addPhone("4950009988", PhoneType.LANDLINE);
        users.add(newUser);

        newUser = new User(4, "Maks", 44);
        users.add(newUser);

        System.out.println(sumAgeByName(users, "Maks"));
        System.out.println(getUserNames(users));
        System.out.println(existOlderUser(users, 70));
        System.out.println(getIdAndNameMap(users));
        System.out.println(groupUsersByAge(users));
        System.out.println(findAllPhoneNumbers(users));
        System.out.println(findOldestUserWithLandlinePhone(users));
    }

    public static User findOldestUserWithLandlinePhone(List<User> users) {
        return users.stream()
                .filter(user -> user.getPhones().stream().anyMatch(Phone::isLandline))
                .max(Comparator.comparing(User::getAge)).get();
    }

    public static String findAllPhoneNumbers(List<User> users) {
        return users.stream()
                .flatMap(user -> user.getPhones().stream())
                .map(phone -> phone.getNumber())
                .reduce((s, s2) -> s +", "+ s2).orElse("нет данных");

    }

    public static Map<Integer, List<User>> groupUsersByAge(List<User> users) {
        return users.stream()
                .collect(Collectors.groupingBy(User::getAge));
    }

    public static Map<Integer, String> getIdAndNameMap(List<User> users) {
       return users.stream()
                .collect(
                        Collectors.toMap(
                                User::getId, User::getName,
                                (oldValue, newValue) -> oldValue,
                                LinkedHashMap::new
                        ));
    }

    public static boolean existOlderUser(List<User> users, int age) {
        return users.stream().anyMatch(user -> user.getAge() > age);
    }

    public static Set<String> getUserNames(List<User> users) {
        return users.stream()
                .map(user -> user.getName())
                .collect(Collectors.toCollection( LinkedHashSet::new ));
    }

    public static Integer sumAgeByName(List<User> users, String name){
        return users.stream()
                .filter(user -> name.equals(user.getName()))
                .map(user -> user.getAge())
                .reduce((a1, a2) -> a1 + a2).orElse(0);
    }
}
