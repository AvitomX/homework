package ru.tfs.collections_generics;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.tfs.collections_generics.task3.Loader;
import ru.tfs.collections_generics.task3.PhoneType;
import ru.tfs.collections_generics.task3.User;

import java.util.*;

public class Task3Test extends TestCase {

    @Test
    public void test() {
        List<User> users = new ArrayList<>();

        User alex = new User(1, "Alex", 10);
        alex.addPhone("9253334455", PhoneType.MOBILE);
        alex.addPhone("9254445566", PhoneType.MOBILE);
        alex.addPhone("4958889900", PhoneType.LANDLINE);
        users.add(alex);

        User maks = new User(2, "Maks", 10);
        maks.addPhone("9255556677", PhoneType.MOBILE);
        maks.addPhone("4950009966", PhoneType.LANDLINE);
        users.add(maks);

        User vasya = new User(3, "Vasya", 68);
        vasya.addPhone("4950009988", PhoneType.LANDLINE);
        users.add(vasya);

        User maks2 = new User(4, "Maks", 44);
        users.add(maks2);

        Assert.assertEquals(Loader.sumAgeByName(users, "Maks"), Optional.of(54).get());

        Assert.assertEquals(Loader.getUserNames(users), Set.of("Alex", "Maks", "Vasya"));

        Assert.assertTrue(Loader.existOlderUser(users, 50));
        Assert.assertFalse(Loader.existOlderUser(users, 68));

        Assert.assertEquals(Loader.getIdAndNameMap(users), Map.of(
                1, "Alex",
                2, "Maks",
                3, "Vasya",
                4,"Maks"
        ));

        Map<Integer, List<User>> actual = new HashMap<>();
        actual.put(10, List.of(alex, maks));
        actual.put(68, List.of(vasya));
        actual.put(44, List.of(maks2));
        Assert.assertEquals(Loader.groupUsersByAge(users), actual);

        String phonesActual = "9253334455, 9254445566, 4958889900, 9255556677, 4950009966, 4950009988";
        Assert.assertEquals(Loader.findAllPhoneNumbers(users), phonesActual);

        Assert.assertEquals(Loader.findOldestUserWithLandlinePhone(users), vasya);
    }

}
