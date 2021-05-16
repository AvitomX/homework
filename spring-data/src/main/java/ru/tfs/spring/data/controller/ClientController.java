package ru.tfs.spring.data.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.clients.Client;
import ru.tfs.spring.data.exception.UserPhoneUniqueException;
import ru.tfs.spring.data.service.client.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullClient.class)
    public ResponseEntity<Client> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.FullClient.class)
    public ResponseEntity<Client> create(@Valid @RequestBody Client client) throws UserPhoneUniqueException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.create(client));
    }
}
