package ru.tfs.spring.data.controller.dict;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.dict.Currency;
import ru.tfs.spring.data.service.dict.CurrencyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullCurrency.class)
    public ResponseEntity<Currency> get(@PathVariable Long id){
        return ResponseEntity.ok(currencyService.get(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullCurrency.class)
    public ResponseEntity<List<Currency>> list(){
        return ResponseEntity.ok(currencyService.getAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullCurrency.class)
    public ResponseEntity<Currency> create(@Valid @RequestBody Currency currency){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(currencyService.create(currency));
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullCurrency.class)
    public ResponseEntity<Currency> update(@PathVariable Long id, @Valid @RequestBody Currency currency){
        return ResponseEntity.ok(currencyService.update(id, currency));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        currencyService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
