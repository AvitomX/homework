package ru.tfs.spring.data.service.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.dto.ParamTypePageDto;
import ru.tfs.spring.data.entity.dict.ParameterType;
import ru.tfs.spring.data.repo.dict.ParameterTypeRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ParameterTypeService {
    private final ParameterTypeRepo parameterTypeRepo;

    @Autowired
    public ParameterTypeService(ParameterTypeRepo parameterTypeRepo) {
        this.parameterTypeRepo = parameterTypeRepo;
    }

    @Transactional
    public ParameterType create(ParameterType parameterType) {
        return parameterTypeRepo.save(parameterType);
    }

    @Transactional(readOnly = true)
    public ParameterType get(Long id) {
        if (id == null)
            new NullPointerException("Parameter type id can't be NULL");

        return parameterTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ParameterType with ID = " + id + " not exists"));
    }

    @Transactional(readOnly = true)
    public ParamTypePageDto getAll(Pageable pageable) {
        Page<ParameterType> page = parameterTypeRepo.findAll(pageable);
        return new ParamTypePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    @Transactional
    public ParameterType update(Long id, ParameterType parameterType) {
        if (parameterType != null) {
            ParameterType parameterTypeFromDB = parameterTypeRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("ParameterType with ID = " + id + " not exists"));
            parameterTypeFromDB.setName(parameterType.getName());
            parameterTypeFromDB.setMeasure(parameterType.getMeasure());
            return parameterTypeRepo.save(parameterTypeFromDB);
        } else {
            throw new NullPointerException("ParameterType is NULL");
        }
    }

    @Transactional
    public void delete(Long id) {
        parameterTypeRepo.deleteById(id);
    }
}
