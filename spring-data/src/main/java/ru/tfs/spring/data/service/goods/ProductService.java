package ru.tfs.spring.data.service.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.dto.CyProductDto;
import ru.tfs.spring.data.dto.ProductDto;
import ru.tfs.spring.data.dto.ProductPageDto;
import ru.tfs.spring.data.entity.dict.Currency;
import ru.tfs.spring.data.entity.goods.Info;
import ru.tfs.spring.data.entity.goods.Parameter;
import ru.tfs.spring.data.entity.goods.Product;
import ru.tfs.spring.data.repo.goods.ProductRepo;
import ru.tfs.spring.data.service.dict.CurrencyService;
import ru.tfs.spring.data.service.dict.LanguageService;
import ru.tfs.spring.data.service.dict.ParameterTypeService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final LanguageService languageService;
    private final ParameterTypeService typeService;
    private final CurrencyService currencyService;

    @Autowired
    public ProductService(ProductRepo productRepo,
                          LanguageService languageService,
                          ParameterTypeService typeService,
                          CurrencyService currencyService) {
        this.productRepo = productRepo;
        this.languageService = languageService;
        this.typeService = typeService;
        this.currencyService = currencyService;
    }

    @Transactional
    public Product create(Product product) {
        if (product != null) {
            Product newProduct = mapToProduct(product);
            return productRepo.save(newProduct);
        } else {
            throw new NullPointerException("Product can't be NULL");
        }
    }

    private Info mapToInfo(Product product, Info source) {
        Info info = new Info();
        info.setTitle(source.getTitle());
        info.setDescription(source.getDescription());
        info.setProduct(product);
        Long languageId = source.getLanguage().getId();
        info.setLanguage(languageService.get(languageId));
        return info;
    }

    private Parameter mapToParameter(Product product, Parameter source) {
        Parameter parameter = new Parameter();
        parameter.setValue(source.getValue());
        parameter.setProduct(product);
        Long typeId = source.getType().getId();
        parameter.setType(typeService.get(typeId));
        return parameter;
    }

    private Product mapToProduct(Product source) {
        Product product = new Product();
        product.setUePrice(source.getUePrice());

        if (!source.getParameters().isEmpty()) {
            for (Parameter parameter : source.getParameters()) {
                Parameter newParameter = mapToParameter(product, parameter);
                product.addParameter(newParameter);
            }
        } else {
            throw new NullPointerException("Product must have at least one parameter!");
        }

        if (!source.getInfos().isEmpty()) {
            for (Info info : source.getInfos()) {
                Info newInfo = mapToInfo(product, info);
                product.addInfo(newInfo);
            }
        } else {
            throw new NullPointerException("Product must have at least one info!");
        }

        return product;
    }

    @Transactional
    public Product update(Long id, Product product) {
        Product productFromDB = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID = " + id + " not exists"));

        productFromDB.setUePrice(product.getUePrice());

        return productFromDB;
    }

    @Transactional
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Product getOne(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID = " + id + " not exists"));
        return product;
    }

    @Transactional(readOnly = true)
    public CyProductDto getOneByCurrency(Long id, Long currencyId) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID = " + id + " not exists"));
        Currency currency = currencyService.get(currencyId);

        return new CyProductDto(product, currency);
    }

    @Transactional(readOnly = true)
    public ProductPageDto getAll(Pageable pageable) {
        Page<Product> page = productRepo.findAll(pageable);
        List<Product> products = page.getContent();

        if (!products.isEmpty()) {
            List<ProductDto> dtoList = products.stream()
                    .map(product -> new ProductDto(product.getId(), product.getUePrice()))
                    .collect(Collectors.toList());
            return new ProductPageDto(
                    dtoList,
                    pageable.getPageNumber(),
                    page.getTotalPages()
            );
        } else {
            throw new EntityNotFoundException("There are no products");
        }
    }
}
