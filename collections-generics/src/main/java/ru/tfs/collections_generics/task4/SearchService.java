package ru.tfs.collections_generics.task4;

import java.util.List;

public interface SearchService {
    List<User> searchForFriendsInWidth(User me, String name);
    List<User> searchForFriendsInDepth(User me, String name);
}
