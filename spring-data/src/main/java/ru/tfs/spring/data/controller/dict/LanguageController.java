package ru.tfs.spring.data.controller.dict;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.dict.Language;
import ru.tfs.spring.data.service.dict.LanguageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguageController {
    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.IdName.class)
    public ResponseEntity<Language> get(@PathVariable Long id){
        return ResponseEntity.ok(languageService.get(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.IdName.class)
    public ResponseEntity<List<Language>> list(){
        return ResponseEntity.ok(languageService.getAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.IdName.class)
    public ResponseEntity<Language> create(@Valid @RequestBody Language language){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(languageService.create(language));
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.IdName.class)
    public ResponseEntity<Language> update(@PathVariable Long id, @Valid @RequestBody Language language){
        return ResponseEntity.ok(languageService.update(id, language));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        languageService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
