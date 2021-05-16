package ru.tfs.read.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "value"})
public class Parameter {
    private Long id;

    private String value;

    private ParameterType type;
}
