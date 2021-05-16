package ru.tfs.spring.data.service.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.entity.dict.Language;
import ru.tfs.spring.data.entity.goods.Info;
import ru.tfs.spring.data.entity.goods.Parameter;
import ru.tfs.spring.data.entity.goods.Product;
import ru.tfs.spring.data.repo.goods.InfoRepo;
import ru.tfs.spring.data.service.dict.LanguageService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InfoService {
    private final InfoRepo infoRepo;
    private final LanguageService languageService;
    private final ProductService productService;

    @Autowired
    public InfoService(InfoRepo infoRepo, LanguageService languageService, ProductService productService) {
        this.infoRepo = infoRepo;
        this.languageService = languageService;
        this.productService = productService;
    }

    private void validate(Info info) {
        if (info != null) {
            Language language = info.getLanguage();
            Long id = language.getId();
            language = languageService.get(id);
            info.setLanguage(language);
        } else {
            throw new NullPointerException("Info can't be NULL");
        }
    }

    @Transactional
    public Info create(Info info, Product product) {
        validate(info);

        Info newInfo = new Info();
        newInfo.setTitle(info.getTitle());
        newInfo.setDescription(info.getDescription());
        newInfo.setProduct(product);
        newInfo.setLanguage(info.getLanguage());

        return infoRepo.save(newInfo);
    }

    @Transactional
    public Info update(Long id, Info info) {
        Info infoFromDB = infoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Info with " + id + " not found"));

        if (info.getTitle() != null)
            infoFromDB.setTitle(info.getTitle());

        if (info.getDescription() != null)
            infoFromDB.setDescription(info.getDescription());

        Product product = info.getProduct();
        if (product != null && product.getId() != null) {
            product = productService.getOne(product.getId());
            infoFromDB.setProduct(product);
        }

        Language language = info.getLanguage();
        if (language != null && language.getId() != null) {
            language = languageService.get(language.getId());
            infoFromDB.setLanguage(language);
        }

        return infoFromDB;
    }

    @Transactional
    public void delete(Long id){
        infoRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Info getOne(Long id) {
        return infoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Info with " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Info> getAllByProduct(Product product) {
        if (product != null) {
            List<Info> list = infoRepo.findAllByProduct(product);
            if (list.isEmpty())
                throw new EntityNotFoundException("Infos not exist");

            return list;
        } else {
            throw new NullPointerException("Product is NULL");
        }
    }
}
