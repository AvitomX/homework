package ru.tfs.compositor.dto;

import lombok.Data;
import ru.tfs.compositor.domain.Currency;
import ru.tfs.compositor.domain.Info;
import ru.tfs.compositor.domain.Parameter;
import ru.tfs.compositor.domain.Product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProductDto {
    private Long productId;
    private BigDecimal cyPrice;
    private Set<Parameter> parameters = new HashSet<>();
    private Info info;

    public ProductDto(Product product, Currency currency) {
        this.productId = product.getProductId();
        this.cyPrice = currency.getMultiplier().multiply(new BigDecimal(product.getUePrice()));
        this.parameters = product.getParameters();
        this.info = product.getInfo();
    }
}
