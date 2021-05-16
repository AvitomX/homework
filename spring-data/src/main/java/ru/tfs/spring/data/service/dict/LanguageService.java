package ru.tfs.spring.data.service.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.dict.Language;
import ru.tfs.spring.data.repo.dict.LanguageRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepo languageRepo;

    @Autowired
    public LanguageService(LanguageRepo languageRepo) {
        this.languageRepo = languageRepo;
    }

    @Transactional
    public Language create(Language language) {
        return languageRepo.save(language);
    }

    @Transactional(readOnly = true)
    public Language get(Long id) {
        return languageRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Language with ID = " + id + " not exists"));
    }

    @Transactional(readOnly = true)
    public List<Language> getAll() {
        return languageRepo.findAll();
    }

    @Transactional
    public Language update(Long id, Language language) {
        if (language != null) {
            Language languageFromDB = languageRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Language with ID = " + id + " not exists"));
            languageFromDB.setName(language.getName());
            return languageRepo.save(languageFromDB);
        } else {
            throw new NullPointerException("Language is NULL");
        }
    }

    @Transactional
    public void delete(Long id) {
        languageRepo.deleteById(id);
    }
}
