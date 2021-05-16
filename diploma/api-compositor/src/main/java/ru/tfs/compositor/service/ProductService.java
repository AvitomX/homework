package ru.tfs.compositor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tfs.compositor.domain.Currency;
import ru.tfs.compositor.domain.Product;
import ru.tfs.compositor.dto.ProductDto;
import ru.tfs.compositor.exception.EntityNotExistsException;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String PRODUCT_URI = "http://read-api/api/product/{productId}/lang/{langId}";
    public static final String CURRENCY_URI = "http://core/currencies/{id}";

    @Autowired
    private final WebClient.Builder webClientBuilder;

    public Mono<ProductDto> getByCash(Long productId, Long langId, Long cyId) {
        Mono<Product> product = getProduct(productId, langId);

        Mono<Currency> currency = getCurrency(cyId);

        return product.flatMap(p -> currency.map(c -> new ProductDto(p, c)))
                .onErrorResume(
                        e -> Mono.error(new EntityNotExistsException(e.getMessage()))
                );
    }

    private Mono<Product> getProduct(Long productId, Long langId) {
        return webClientBuilder.build()
                .get()
                .uri(PRODUCT_URI, productId, langId)
                .exchange()
                .flatMap(response -> {
                    if ( response.statusCode().isError() ) {
                        return Mono.error(new EntityNotExistsException("Such product not exists"));
                    }
                    return response.bodyToMono(Product.class);
                });
    }

    private Mono<Currency> getCurrency(Long cyId) {
        return webClientBuilder.build()
                .get()
                .uri(CURRENCY_URI, cyId)
                .exchange()
                .flatMap(response -> {
                    if ( response.statusCode().isError() ) {
                        return Mono.error(new EntityNotExistsException("Currency not exists with ID = " + cyId ));
                    }
                    return response.bodyToMono(Currency.class);
                });
    }
}
