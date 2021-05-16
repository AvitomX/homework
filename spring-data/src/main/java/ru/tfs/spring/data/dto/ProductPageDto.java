package ru.tfs.spring.data.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.entity.Views;

import java.util.List;

@Setter
@Getter
public class ProductPageDto {
    @JsonView(Views.IdName.class)
    private List<ProductDto> list;

    @JsonView(Views.IdName.class)
    private int currentPage;

    @JsonView(Views.IdName.class)
    private int totalPage;

    public ProductPageDto() {
    }

    public ProductPageDto(List<ProductDto> list, int currentPage, int totalPage) {
        this.list = list;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
