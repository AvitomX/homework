package ru.tfs.spring.data.controller.goods;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.goods.Parameter;
import ru.tfs.spring.data.entity.goods.Product;
import ru.tfs.spring.data.service.goods.ParameterService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ParameterController {
    private final ParameterService parameterService;

    @Autowired
    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @PostMapping(value = "/products/{productId}/param", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameter.class)
    public ResponseEntity<Parameter> add(
            @PathVariable("productId") Product product,
            @Valid @RequestBody Parameter parameter){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(parameterService.create(parameter, product));
    }

    @PutMapping(value = "/param/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameter.class)
    public ResponseEntity<Parameter> update(
            @PathVariable("id") Long id,
            @RequestBody Parameter parameter){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parameterService.update(id, parameter));
    }

    @GetMapping(value = "/param/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameter.class)
    public ResponseEntity<Parameter> get(@PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parameterService.getOne(id));
    }

    @GetMapping(value = "/products/{productId}/param", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullParameter.class)
    public ResponseEntity<List<Parameter>> getByProduct(@PathVariable("productId") Product product){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parameterService.getAllByProduct(product));
    }

    @DeleteMapping("/param/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        parameterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
