package ru.tfs.spring.data.repo.dict;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.data.entity.dict.Language;

public interface LanguageRepo extends JpaRepository<Language, Long> {
}
