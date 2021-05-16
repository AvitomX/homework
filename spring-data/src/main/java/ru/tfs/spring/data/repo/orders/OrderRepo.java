package ru.tfs.spring.data.repo.orders;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.orders.Order;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = { "items", "client", "shipment", "currency" })
    @Override
    Optional<Order> findById(Long aLong);
}
