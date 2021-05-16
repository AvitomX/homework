package ru.tfs.read.api.repo;

import org.springframework.stereotype.Component;
import ru.tfs.read.api.domain.Product;
import ru.tfs.read.api.exception.EntityNotExistsException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ProductRepo {
    private final Set<Product> products = new CopyOnWriteArraySet<>();

    public void save(List<Product> list){
        list.stream().forEach(v -> products.add(v));
    }

    public Product findById(Long id) throws EntityNotExistsException {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotExistsException("Product not exists"));
    }
}
