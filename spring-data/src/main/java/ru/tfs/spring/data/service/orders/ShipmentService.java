package ru.tfs.spring.data.service.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.orders.Shipment;
import ru.tfs.spring.data.repo.orders.ShipmentRepo;

import javax.persistence.EntityNotFoundException;

@Service
public class ShipmentService {
    private final ShipmentRepo shipmentRepo;

    @Autowired
    public ShipmentService(ShipmentRepo shipmentRepo) {
        this.shipmentRepo = shipmentRepo;
    }


    @Transactional(readOnly = true)
    public Shipment getById(Long id) {
        return shipmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment with ID = " + id + " not exists"));
    }
}
