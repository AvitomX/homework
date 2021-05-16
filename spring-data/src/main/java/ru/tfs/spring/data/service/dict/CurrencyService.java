package ru.tfs.spring.data.service.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.dict.Currency;
import ru.tfs.spring.data.repo.dict.CurrencyRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CurrencyService{
    private final CurrencyRepo currencyRepo;

    @Autowired
    public CurrencyService(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @Transactional
    public Currency create(Currency currency) {
        if (currency != null)
            return currencyRepo.save(currency);
        else
            throw new NullPointerException("Currency is NULL");
    }

    @Transactional(readOnly = true)
    public Currency get(Long id) {
        if (id != null)
            return  currencyRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Currency with ID = " + id + " not exists"));
        else
            throw new NullPointerException("Currency id is NULL");
    }

    @Transactional(readOnly = true)
    public List<Currency> getAll() {
        return currencyRepo.findAll();
    }

    @Transactional
    public Currency update(Long id, Currency currency) {
        if (currency != null) {
            Currency currencyFromDB = currencyRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Currency with ID = " + id + " not exists"));
            currencyFromDB.setName(currency.getName());
            currencyFromDB.setMultiplier(currency.getMultiplier());
            return currencyRepo.save(currencyFromDB);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id) {
        currencyRepo.deleteById(id);
    }
}
