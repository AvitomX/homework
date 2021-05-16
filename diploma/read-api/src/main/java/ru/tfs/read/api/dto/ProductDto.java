package ru.tfs.read.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.tfs.read.api.domain.Info;
import ru.tfs.read.api.domain.Parameter;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"productId", "uePrice", "info"})
public class ProductDto {
    private Long productId;
    private Long uePrice;
    private Set<Parameter> parameters = new HashSet<>();
    private Info info;

    public void addParam(Parameter parameter){
        parameters.add(parameter);
    }
}
