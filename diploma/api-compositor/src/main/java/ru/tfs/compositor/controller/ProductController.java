package ru.tfs.compositor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.tfs.compositor.dto.ProductDto;
import ru.tfs.compositor.exception.EntityNotExistsException;
import ru.tfs.compositor.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}/lang/{langId}")
    public Mono<ResponseEntity> getByCash(
            @PathVariable Long productId,
            @PathVariable Long langId,
            @RequestParam(required = true, name = "cy") Long cyId
    ) {
        return productService.getByCash(productId, langId, cyId).map(productDto -> ResponseEntity.ok(productDto));
    }
}
