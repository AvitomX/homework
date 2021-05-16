package ru.tfs.spring.data.controller.goods;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.dto.CyProductDto;
import ru.tfs.spring.data.dto.ProductDto;
import ru.tfs.spring.data.dto.ProductPageDto;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.goods.Product;
import ru.tfs.spring.data.service.goods.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullProduct.class)
    public ResponseEntity<Product> create(@Valid @RequestBody Product product){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(product));
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullProduct.class)
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @Valid @RequestBody Product product) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.update(id, product));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.IdName.class)
    public ResponseEntity<ProductPageDto> list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 50) Pageable pageable
    ) {
        ProductPageDto pageDto = productService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pageDto);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullProduct.class)
    public ResponseEntity<?> getByCurrency(
            @PathVariable("id") Long id,
            @RequestParam(required = false, name = "cy") Long currencyId
    ) {
        if (currencyId != null) {
            CyProductDto dto = productService.getOneByCurrency(id, currencyId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dto);
        } else {
            Product product = productService.getOne(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(product);
        }

    }

}
