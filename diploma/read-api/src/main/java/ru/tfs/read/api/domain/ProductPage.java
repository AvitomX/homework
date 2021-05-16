package ru.tfs.read.api.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductPage {
    private List<Product> list;

    private int currentPage;

    private int totalPage;

    public ProductPage() {
    }

    public ProductPage(List<Product> list, int currentPage, int totalPage) {
        this.list = list;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
