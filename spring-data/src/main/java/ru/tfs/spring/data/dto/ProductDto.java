package ru.tfs.spring.data.dto;

import com.fasterxml.jackson.annotation.JsonView;
import ru.tfs.spring.data.entity.Views;

public class ProductDto {
    @JsonView(Views.IdName.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private Long uePrice;

    public ProductDto(Long id, Long uePrice) {
        this.id = id;
        this.uePrice = uePrice;
    }
}
