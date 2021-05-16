package ru.tfs.spring.data.repo.goods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.goods.Product;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    boolean existsById(Long id);

    @EntityGraph(value = "graph.Product-Parameters-Infos")
    Optional<Product> findById(Long id);

    @Override
    Page<Product> findAll(Pageable pageable);
}
