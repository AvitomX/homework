package ru.tfs.spring.data.controller.dict;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.dto.ParamTypePageDto;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.dict.ParameterType;
import ru.tfs.spring.data.service.dict.ParameterTypeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/parameterType")
public class ParameterTypeController {
    private final ParameterTypeService parameterTypeService;

    @Autowired
    public ParameterTypeController(ParameterTypeService parameterTypeService) {
        this.parameterTypeService = parameterTypeService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameterType.class)
    public ResponseEntity<ParameterType> get(@PathVariable Long id){
        return ResponseEntity.ok(parameterTypeService.get(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameterType.class)
    public ResponseEntity<ParamTypePageDto> list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC)Pageable pageable
            ){
        return ResponseEntity.ok(parameterTypeService.getAll(pageable));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameterType.class)
    public ResponseEntity<ParameterType> create(@Valid @RequestBody ParameterType parameterType){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(parameterTypeService.create(parameterType));
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameterType.class)
    public ResponseEntity<ParameterType> update(@PathVariable Long id, @Valid @RequestBody ParameterType parameterType){
        return ResponseEntity.ok(parameterTypeService.update(id, parameterType));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        parameterTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
