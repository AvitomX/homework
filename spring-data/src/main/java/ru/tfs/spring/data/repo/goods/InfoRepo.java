package ru.tfs.spring.data.repo.goods;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.goods.Info;
import ru.tfs.spring.data.entity.goods.Product;

import java.util.List;
import java.util.Optional;

public interface InfoRepo extends JpaRepository<Info, Long> {

    @EntityGraph(attributePaths = { "language" })
    Optional<Info> findById(Long aLong);

    @EntityGraph(attributePaths = { "language" })
    List<Info> findAllByProduct(Product product);
}
