package ru.tfs.spring.data.repo.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.orders.Shipment;

public interface ShipmentRepo extends JpaRepository<Shipment, Long> {
}
