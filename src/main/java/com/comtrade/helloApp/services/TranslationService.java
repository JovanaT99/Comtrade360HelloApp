package com.comtrade.helloApp.services;

import com.comtrade.helloApp.models.Translation;
import com.comtrade.helloApp.repositories.TranslationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TranslationService {

    private final TranslationRepository translationRepository;

    public List<Translation> all() {
        return translationRepository.findAll();
    }

    public String getTranslate(String language) {

        if (language == null || language.isEmpty()) {
            language = "en";
        }

        Translation optionalTranslation = translationRepository.findByLanguage(language);

        if (optionalTranslation ==null) {

            throw new IllegalStateException("Language doesnt exist in datebase");
        }
        return optionalTranslation.getTranslation();
    }
    public RedirectView add(String language, String translation) {
        Optional<Translation> check = Optional.ofNullable(translationRepository.findByLanguage(language));
        if (check.isPresent()) {
            return new RedirectView("/secure/translations?msg=Language taken");
        } else if (language.isEmpty() || translation.isEmpty()) {
            return new RedirectView("/secure/translations?msg=Invalid input");
        } else {
            translationRepository.save(new Translation(language, translation));
        }
        return new RedirectView("/secure/translations?msg=Added");
    }


    public RedirectView delete(String language) {
        Optional<Translation> check = Optional.ofNullable(translationRepository.findByLanguage(language));
        if (check.isEmpty()) {
            return new RedirectView("/secure/translations?msg=Language doesnt exist");
        }
        translationRepository.delete(check.get());
        return new RedirectView("/secure/translations?msg=Deleted");
    }
}
