package ru.tfs.exampleApp.service;

import java.util.UUID;

public interface ApiService {
    String get();

    void delete(UUID uuid);
}
