package ru.tfs.exampleApp.repo;

import org.springframework.stereotype.Repository;
import ru.tfs.service.Timed;

import java.util.Random;
import java.util.UUID;

@Repository
public class ApiRepoImpl implements ApiRepo {
    private final Random random = new Random();

    public ApiRepoImpl() {
    }

    @Override
    public String find() {
        work();
        return UUID.randomUUID().toString();
    }

    @Timed
    @Override
    public void delete() {
        work();
        System.out.println("delete REPO");
    }


    private void work() {
        try {
            Thread.sleep((long) (1000 * random.nextDouble()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
