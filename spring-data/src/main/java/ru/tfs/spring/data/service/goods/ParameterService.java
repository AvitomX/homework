package ru.tfs.spring.data.service.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.dict.ParameterType;
import ru.tfs.spring.data.entity.goods.Parameter;
import ru.tfs.spring.data.entity.goods.Product;
import ru.tfs.spring.data.repo.goods.ParameterRepo;
import ru.tfs.spring.data.service.dict.ParameterTypeService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ParameterService {
    private final ParameterRepo parameterRepo;
    private final ParameterTypeService parameterTypeService;
    private final ProductService productService;

    @Autowired
    public ParameterService(ParameterRepo parameterRepo, ParameterTypeService parameterTypeService, ProductService productService) {
        this.parameterRepo = parameterRepo;
        this.parameterTypeService = parameterTypeService;
        this.productService = productService;
    }

    @Transactional
    public Parameter create(Parameter parameter, Product product) {
        if (parameter != null && product != null){
            Parameter newParam = new Parameter();

            newParam.setValue(parameter.getValue());
            newParam.setProduct(product);
            ParameterType type = parameter.getType();
            if (type != null) {
                Long typeId = type.getId();
                type = parameterTypeService.get(typeId);
                newParam.setType(type);
            }

            return parameterRepo.save(newParam);
        } else {
            throw new NullPointerException("Parameter or product can't be NULL");
        }
    }

    @Transactional
    public Parameter update(Long id, Parameter parameter) {
        if (parameter != null && id != null){
            Parameter paramFromDB = parameterRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Parameter with " + id + " not found"));

            if (parameter.getValue() != null)
                paramFromDB.setValue(parameter.getValue());

            Product product = parameter.getProduct();
            if (product != null) {
                Long productId = product.getId();
                Product productFromDB = productService.getOne(productId);
                paramFromDB.setProduct(productFromDB);
            }

            ParameterType type = parameter.getType();
            if (type != null) {
                Long typeId = type.getId();
                type = parameterTypeService.get(typeId);
                paramFromDB.setType(type);
            }

            return paramFromDB;
        } else {
            throw new NullPointerException("Parameter can't be NULL");
        }
    }

    @Transactional(readOnly = true)
    public Parameter getOne(Long id) {
        Parameter parameter = parameterRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parameter with " + id + " not found"));

        return parameter;
    }

    public void delete(Long id) {
        parameterRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Parameter> getAllByProduct(Product product) {
        if (product != null) {
            List<Parameter> list = parameterRepo.findAllByProduct(product);
            if (list.isEmpty())
                throw new EntityNotFoundException("Parameters not exist");

            return list;
        } else {
            throw new NullPointerException("Product is NULL");
        }

    }
}
