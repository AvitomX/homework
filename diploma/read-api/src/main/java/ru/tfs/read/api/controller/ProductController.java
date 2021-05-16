package ru.tfs.read.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.read.api.dto.ProductDto;
import ru.tfs.read.api.exception.EntityNotExistsException;
import ru.tfs.read.api.service.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}/lang/{langId}")
    public ResponseEntity<ProductDto> getOneByLang(
            @PathVariable Long productId,
            @PathVariable Long langId
    ) throws EntityNotExistsException {
        Optional<ProductDto> dto = productService.getByIdAndLang(productId, langId);
        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
