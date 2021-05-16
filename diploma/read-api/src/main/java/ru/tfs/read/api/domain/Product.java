package ru.tfs.read.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "uePrice"})
public class Product {
    private Long id;
    private Long uePrice;
}
