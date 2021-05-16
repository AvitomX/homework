package ru.tfs.spring.data.repo.client;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.clients.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {
    boolean existsByPhone(String phone);
}
