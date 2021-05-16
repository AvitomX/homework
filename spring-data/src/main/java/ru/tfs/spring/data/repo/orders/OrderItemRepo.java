package ru.tfs.spring.data.repo.orders;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.orders.OrderItem;

import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    @EntityGraph(attributePaths = { "product", "order" })
    @Override
    Optional<OrderItem> findById(Long aLong);
}
