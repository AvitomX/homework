package ru.tfs.spring.data.service.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.clients.Client;
import ru.tfs.spring.data.entity.dict.Currency;
import ru.tfs.spring.data.entity.orders.Order;
import ru.tfs.spring.data.entity.orders.OrderItem;
import ru.tfs.spring.data.entity.orders.Shipment;
import ru.tfs.spring.data.repo.orders.OrderRepo;
import ru.tfs.spring.data.service.client.ClientService;
import ru.tfs.spring.data.service.dict.CurrencyService;
import ru.tfs.spring.data.service.dict.LanguageService;

import javax.persistence.EntityNotFoundException;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderItemService itemService;
    private final CurrencyService currencyService;
    private final ClientService clientService;
    private final ShipmentService shipmentService;

    @Autowired
    public OrderService(
            OrderRepo orderRepo,
            OrderItemService itemService,
            CurrencyService currencyService,
            ClientService clientService,
            ShipmentService shipmentService,
            LanguageService languageService) {
        this.orderRepo = orderRepo;
        this.itemService = itemService;
        this.currencyService = currencyService;
        this.clientService = clientService;
        this.shipmentService = shipmentService;
    }

    @Transactional
    public Order create(Order order) {
        Order newOrder = new Order();

        updateCurrency(newOrder, order.getCurrency());
        for (OrderItem item : order.getItems()) {
            newOrder.addItem(itemService.add(item, newOrder));
        }
        updateClient(newOrder, order.getClient());
        updateShipment(newOrder, order.getShipment());

        return orderRepo.save(newOrder);
    }


    @Transactional
    public Order update(Long id, Order order) {
        Order orderFromDB = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID = " + id + " not exists"));
        updateCurrency(orderFromDB, order.getCurrency());
        updateClient(orderFromDB, order.getClient());
        updateShipment(orderFromDB, order.getShipment());

        return orderFromDB;
    }


    @Transactional(readOnly = true)
    public Order getOne(Long id) {
        Order orderFromDB = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID = " + id + " not exists"));

        return orderFromDB;
    }

    private void updateCurrency(Order order, Currency currency) {
        Long cyId = currency.getId();
        Currency currencyFromDB = currencyService.get(cyId);
        order.setCurrency(currencyFromDB);
    }

    private void updateShipment(Order order, Shipment shipment) {
        if (shipment != null && shipment.getId() != null ){
            shipment = shipmentService.getById(shipment.getId());
            order.setShipment(shipment);
        }
    }

    private void updateClient(Order order, Client client) {
        if (client != null && client.getId() != null ){
            client = clientService.getById(client.getId());
            order.setClient(client);
        }
    }

}
