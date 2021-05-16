package ru.tfs.spring.data.controller.orders;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.orders.Order;
import ru.tfs.spring.data.service.orders.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullOrder.class)
    public ResponseEntity<Order> add(@Valid @RequestBody Order order) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.create(order));
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullOrder.class)
    public ResponseEntity<Order> update(@PathVariable("id") Long id, @Valid @RequestBody Order order) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.update(id, order));
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullOrder.class)
    public ResponseEntity<Order> get(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getOne(id));
    }
}
