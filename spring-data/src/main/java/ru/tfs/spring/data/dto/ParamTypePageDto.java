package ru.tfs.spring.data.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.dict.ParameterType;

import java.util.List;

@Getter
@Setter
public class ParamTypePageDto {
    @JsonView(Views.FullParameterType.class)
    private List<ParameterType> types;

    @JsonView(Views.FullParameterType.class)
    private int currentPage;

    @JsonView(Views.FullParameterType.class)
    private int totalPage;

    public ParamTypePageDto() {
    }

    public ParamTypePageDto(List<ParameterType> types, int currentPage, int totalPage) {
        this.types = types;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
