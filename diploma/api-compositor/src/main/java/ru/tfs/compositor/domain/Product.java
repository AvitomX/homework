package ru.tfs.compositor.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"productId", "uePrice", "info"})
public class Product {
    private Long productId;
    private Long uePrice;
    private Set<Parameter> parameters = new HashSet<>();
    private Info info;
}
