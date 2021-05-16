package ru.tfs.spring.data.service.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.goods.Product;
import ru.tfs.spring.data.entity.orders.Order;
import ru.tfs.spring.data.entity.orders.OrderItem;
import ru.tfs.spring.data.repo.orders.OrderItemRepo;
import ru.tfs.spring.data.repo.orders.OrderRepo;
import ru.tfs.spring.data.service.goods.ProductService;

import javax.persistence.EntityNotFoundException;

@Service
public class OrderItemService {
    private final OrderItemRepo itemRepo;
    private final ProductService productService;
    private final OrderRepo orderRepo;

    @Autowired
    public OrderItemService(
            OrderItemRepo itemRepo,
            ProductService productService,
            OrderRepo orderRepo) {
        this.itemRepo = itemRepo;
        this.productService = productService;
        this.orderRepo = orderRepo;
    }

    @Transactional(readOnly = true)
    public OrderItem getOne(Long id) {
        OrderItem item = itemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with ID = " + id + " not exists"));

        return item;
    }

    @Transactional
    public OrderItem add(OrderItem item, Order order) {
        OrderItem newItem = new OrderItem();
        updateProduct(newItem, item.getProduct());

        if (order != null)
            newItem.setOrder(order);
        else
            throw new NullPointerException("Order can't be NULL");

        newItem.setQuantity(item.getQuantity());

        return itemRepo.save(newItem);
    }

    @Transactional
    public OrderItem update(OrderItem itemFromDB, OrderItem item) {
        if (item.getProduct() != null)
            updateProduct(itemFromDB, item.getProduct());

        if (item.getOrder() != null)
            updateOrder(itemFromDB, item.getOrder());

        if (item.getQuantity() != null)
            itemFromDB.setQuantity(item.getQuantity());

        return itemFromDB;
    }

    private void updateProduct(OrderItem item, Product product) {
        if (product != null && product.getId() != null) {
            product = productService.getOne(product.getId());
            item.setProduct(product);
        } else {
            throw new NullPointerException("Product or id can't be NULL");
        }
    }

    private void updateOrder(OrderItem item, Order order) {
        if (order != null && order.getId() != null) {
            Long orderId = order.getId();
            order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException("Order with ID = "+ orderId +" not exists"));
            item.setOrder(order);
        }
    }

    public void delete(Long id) {
        itemRepo.deleteById(id);
    }
}
