package ru.tfs.spring.data.repo.goods;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.goods.Parameter;
import ru.tfs.spring.data.entity.goods.Product;

import java.util.List;
import java.util.Optional;

public interface ParameterRepo extends JpaRepository<Parameter, Long> {
    @EntityGraph(attributePaths = { "type" })
    Optional<Parameter> findById(Long aLong);

    @EntityGraph(attributePaths = { "type" })
    List<Parameter> findAllByProduct(Product product);
}
