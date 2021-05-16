package ru.tfs.spring.data.repo.dict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.dict.ParameterType;

public interface ParameterTypeRepo extends JpaRepository<ParameterType, Long> {
    @Override
    Page<ParameterType> findAll(Pageable pageable);
}
