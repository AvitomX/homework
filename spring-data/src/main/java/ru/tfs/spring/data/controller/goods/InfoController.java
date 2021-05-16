package ru.tfs.spring.data.controller.goods;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.goods.Info;
import ru.tfs.spring.data.entity.goods.Parameter;
import ru.tfs.spring.data.entity.goods.Product;
import ru.tfs.spring.data.service.goods.InfoService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @PostMapping(value = "/products/{infoId}/info", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullInfo.class)
    public ResponseEntity<Info> add(
            @PathVariable("infoId") Product product,
            @Valid @RequestBody Info info){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(infoService.create(info, product));
    }

    @PutMapping(value = "/info/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullInfo.class)
    public ResponseEntity<Info> update(
            @PathVariable("id") Long id,
            @RequestBody Info info){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(infoService.update(id, info));
    }

    @GetMapping(value = "/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullInfo.class)
    public ResponseEntity<Info> get(@PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(infoService.getOne(id));
    }

    @GetMapping(value = "/products/{productId}/infos", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullInfo.class)
    public ResponseEntity<List<Info>> getByProduct(@PathVariable("productId") Product product){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(infoService.getAllByProduct(product));
    }

    @DeleteMapping("/info/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        infoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
