package ru.tfs.exampleApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.exampleApp.service.ApiService;
import ru.tfs.service.Timed;
import ru.tfs.service.metrics.MetricStatProvider;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class ApiController {
    private ApiService service;
    private MetricStatProvider provider;

    @Autowired
    public ApiController(ApiService service, MetricStatProvider provider) {
        this.service = service;
        this.provider = provider;
    }

    @GetMapping(value = "value")
    public String get() {
        return service.get();
    }

    @DeleteMapping(value = "value")
    public String delete(){
        UUID uuid = UUID.randomUUID();
        service.delete(uuid);
        return uuid.toString();
    }


    @GetMapping(value = "stat")
    public String getStat(){
        return provider.getTotalStat().toString();
    }
}
