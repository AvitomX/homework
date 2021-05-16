package ru.tfs.read.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.tfs.read.api.client.ProductClient;
import ru.tfs.read.api.domain.Info;
import ru.tfs.read.api.domain.Parameter;
import ru.tfs.read.api.domain.Product;
import ru.tfs.read.api.domain.ProductPage;
import ru.tfs.read.api.dto.ProductDto;
import ru.tfs.read.api.exception.EntityNotExistsException;
import ru.tfs.read.api.repo.DtoProductRepo;
import ru.tfs.read.api.repo.ProductRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    static final int INIT_PAGE = 0;

    private final ProductRepo productRepo;
    private final DtoProductRepo dtoProductRepo;
    private final ProductClient productClient;

    @Autowired
    public ProductService(ProductRepo productRepo, DtoProductRepo dtoProductRepo, ProductClient productClient) {
        this.productRepo = productRepo;
        this.dtoProductRepo = dtoProductRepo;
        this.productClient = productClient;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    private void getAllProduct(){
        ProductPage page = productClient.getProducts(INIT_PAGE);
        int totalPage = page.getTotalPage();
        productRepo.save(page.getList());

        for (int i = INIT_PAGE + 1; i < totalPage; i++) {
            page = productClient.getProducts(i);
            productRepo.save(page.getList());
        }
    }

    public Optional<ProductDto> getByIdAndLang(Long productId, Long langId) throws EntityNotExistsException {
        Optional<ProductDto> dto = dtoProductRepo.findByIdAndLang(productId, langId);
        if (dto.isPresent()){
            return dto;
        } else {
            return mapToDto(productId, langId);
        }
    }

    private Optional<ProductDto> mapToDto(Long productId, Long langId) throws EntityNotExistsException {
        Product product = productRepo.findById(productId);

        List<Parameter> params = productClient.getParams(productId);
        Set<Parameter> parameterSet = new HashSet<>();
        for (Parameter parameter:params) {
            parameterSet.add(parameter);
        }

        List<Info> infos  = productClient.getInfos(productId);

        Optional<ProductDto> dtoOptional = Optional.empty();
        for (Info info: infos) {
            ProductDto dto = new ProductDto();
            dto.setProductId(product.getId());
            dto.setUePrice(product.getUePrice());
            dto.setParameters(parameterSet);
            dto.setInfo(info);

            dtoProductRepo.save(dto);

            if (langId.equals(info.getLanguage().getId()))
                dtoOptional = Optional.of(dto);
        }

        return dtoOptional;
    }
}
