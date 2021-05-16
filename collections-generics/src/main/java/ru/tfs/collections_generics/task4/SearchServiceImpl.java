package ru.tfs.collections_generics.task4;

import java.util.*;

public class SearchServiceImpl implements SearchService {
    private Set<User> visitedUsers;
    private Deque<User> deque;

    @Override
    public List<User> searchForFriendsInWidth(User me, String name) {
        visitedUsers = new HashSet<>();
        deque = new ArrayDeque<>();
        List<User> usersByName = new ArrayList<>();

        bfs(me, name, usersByName);
        return usersByName;
    }

    @Override
    public List<User> searchForFriendsInDepth(User me, String name) {
        visitedUsers = new HashSet<>();
        deque = new ArrayDeque<>();
        List<User> usersByName = new ArrayList<>();

        dfs(me, name, usersByName);
        return usersByName;
    }

    private void dfs(User node, String name, List<User> usersByName) {
        if (name.equals(node.getName())){
            usersByName.add(node);
        }

        visitedUsers.add(node);
        for (User friend: node.getFriends()) {
            if (!visitedUsers.contains(friend) && !deque.contains(friend))
                deque.push(friend);
        }

        User nextNode = deque.pollFirst();
        if (nextNode != null)
            dfs(nextNode, name, usersByName);
    }

    private void bfs(User node, String name, List<User> usersByName) {
        if (name.equals(node.getName())){
            usersByName.add(node);
        }

        visitedUsers.add(node);
        for (User friend: node.getFriends()) {
            if (!visitedUsers.contains(friend) && !deque.contains(friend))
                deque.addLast(friend);
        }

        User nextNode = deque.pollFirst();
        if (nextNode != null)
            bfs(nextNode, name, usersByName);
    }

}
