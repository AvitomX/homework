package ru.tfs.spring.data.dto;

import com.fasterxml.jackson.annotation.JsonView;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.dict.Currency;
import ru.tfs.spring.data.entity.goods.Product;

import java.math.BigDecimal;


public class CyProductDto {
    @JsonView(Views.FullProduct.class)
    private Product product;

    @JsonView(Views.FullProduct.class)
    private BigDecimal cyPrice;

    public CyProductDto(Product product, Currency currency) {
        this.product = product;
        this.cyPrice = currency.getMultiplier().multiply(new BigDecimal(product.getUePrice()));
    }
}
