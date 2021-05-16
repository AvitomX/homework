package ru.tfs.compositor.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Currency {
    private Long id;

    private String name;

    private BigDecimal multiplier;
}
