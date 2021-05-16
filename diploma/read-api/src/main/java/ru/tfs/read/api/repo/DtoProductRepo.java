package ru.tfs.read.api.repo;

import org.springframework.stereotype.Component;
import ru.tfs.read.api.dto.ProductDto;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class DtoProductRepo {
    private final Set<ProductDto> productDtos = new CopyOnWriteArraySet<>();

    public Optional<ProductDto> findByIdAndLang(Long productId, Long langId) {
        if (productId == null || langId == null)
            return Optional.empty();

        return productDtos.stream()
                .filter(val -> productId.equals(val.getProductId())
                        && langId.equals(val.getInfo().getLanguage().getId()))
                .findFirst();
    }

    public void save(ProductDto dto) {
        productDtos.add(dto);
    }
}
