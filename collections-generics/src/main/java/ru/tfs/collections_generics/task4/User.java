package ru.tfs.collections_generics.task4;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class User {
    private Long id;
    private String name;
    private List<User> friends;

    public User(String name) {
        this.name = name;
        this.id = new Random().nextLong();
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<User> getFriends() {
        return friends;
    }
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (31 * hash + id);
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
