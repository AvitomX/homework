package ru.tfs.read.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class ParameterType {
    private Long id;

    private String name;

    private String measure;
}
