package ru.tfs.spring.data.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.clients.Client;
import ru.tfs.spring.data.exception.UserPhoneUniqueException;
import ru.tfs.spring.data.repo.client.ClientRepo;

import javax.persistence.EntityNotFoundException;

@Service
public class ClientService {
    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    private void validate(Client client) throws UserPhoneUniqueException {
        String phone = client.getPhone();
        if (phone != null) {
            if (clientRepo.existsByPhone(phone)) {
                throw new UserPhoneUniqueException("Phone " + phone + " is exist");
            }
        }
    }

    @Transactional
    public Client create(Client client) throws UserPhoneUniqueException {
        validate(client);
        Client newClient = new Client();
        newClient.setName(client.getName());
        newClient.setPhone(client.getPhone());
        newClient.setRegion(client.getRegion());

        return clientRepo.save(newClient);
    }


    @Transactional(readOnly = true)
    public Client getById(Long id) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID = " + id + " not exists"));
        return client;
    }
}
