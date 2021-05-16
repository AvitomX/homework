package ru.tfs.exampleApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tfs.exampleApp.repo.ApiRepo;
import ru.tfs.service.Timed;
import ru.tfs.service.metrics.MetricStatProvider;

import java.util.Random;
import java.util.UUID;

@Service
@Timed
public class ApiServiceImpl implements ApiService {
    private ApiRepo repo;
    private final Random random = new Random();

    public ApiServiceImpl() {
    }

    @Autowired
    public ApiServiceImpl(ApiRepo repo) {
        this.repo = repo;
    }

    @Override
    public String get() {
        work();
        return repo.find();
    }

    @Override
    public void delete(UUID uuid) {
        work();
        repo.delete();
    }

    private void work() {
        try {
            Thread.sleep((long) (1000 * random.nextDouble()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
