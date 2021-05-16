package ru.tfs.read.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tfs.read.api.domain.Info;
import ru.tfs.read.api.domain.Parameter;
import ru.tfs.read.api.domain.ProductPage;

import java.util.List;

@FeignClient(name = "core")
public interface ProductClient {
    @GetMapping("/products")
    ProductPage getProducts(@RequestParam(value = "page") Integer page);

    @GetMapping("/products/{productId}/param")
    List<Parameter> getParams(@PathVariable Long productId);

    @GetMapping("/products/{productId}/infos")
    List<Info> getInfos(@PathVariable Long productId);
}
