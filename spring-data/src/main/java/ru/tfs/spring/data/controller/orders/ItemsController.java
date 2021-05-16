package ru.tfs.spring.data.controller.orders;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.orders.Order;
import ru.tfs.spring.data.entity.orders.OrderItem;
import ru.tfs.spring.data.service.orders.OrderItemService;

import javax.validation.Valid;

@RestController
public class ItemsController {
    private final OrderItemService orderItemService;

    @Autowired
    public ItemsController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping(value = "/orderItems/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullOrderItem.class)
    public ResponseEntity<OrderItem> getOne(@PathVariable("itemId") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderItemService.getOne(id));
    }

    @PostMapping(value = "/order/{orderId}/orderItems", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullOrderItem.class)
    public ResponseEntity<OrderItem> add(
            @PathVariable("orderId") Order order,
            @Valid @RequestBody OrderItem item
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderItemService.add(item, order));
    }

    @PutMapping(value = "/orderItems/{itemId}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullOrderItem.class)
    public ResponseEntity<OrderItem> update(
            @PathVariable("itemId") OrderItem itemFromDB,
            @Valid @RequestBody OrderItem item
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderItemService.update(itemFromDB, item));
    }


    @DeleteMapping(value = "/orderItems/{itemId}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteItem(@PathVariable("itemId") Long id) {
        orderItemService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}
