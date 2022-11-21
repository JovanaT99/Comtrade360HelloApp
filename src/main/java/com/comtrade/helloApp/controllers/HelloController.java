package com.comtrade.helloApp.controllers;

import com.comtrade.helloApp.exceptions.BadRequestException;
import com.comtrade.helloApp.models.Translation;
import com.comtrade.helloApp.services.SystranService;
import com.comtrade.helloApp.services.TranslationService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@AllArgsConstructor
public class HelloController {

    private final TranslationService translationService;

    private final SystranService systranService;

    @Autowired
    private Environment env;

    @GetMapping(value = "/hello-rest", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> helloRest() {
        JSONObject response = new JSONObject();
        response.put("message", "Hello world");
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

//    @GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
//    ResponseEntity<String> hello() {
//        return new ResponseEntity<>("Hello World", HttpStatus.OK);
//    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "source", required = false) String source
    ) {
        if (source == null) {
            source = env.getProperty("spring.translation.source");
        }


        try {
            if (source.equals("api")) {
                return new ResponseEntity<>(systranService.translate(language), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(translationService.getTranslate(language), HttpStatus.OK);

            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @GetMapping("/secure/hello")
    public ResponseEntity<String> secure(
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "source", required = false) String source
    ) {
        return this.hello(language, source);

    }

    @GetMapping(value = "/secure/translations")
    ModelAndView secureList() {

        List<Translation> translations = translationService.all();

        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("translations", translations);

        return modelAndView;
    }

    @PostMapping(value = "/secure/translations/add")
    RedirectView addTranslation(@RequestParam String language, @RequestParam String translation) {
        return translationService.add(language, translation);

    }

    @PostMapping(value = "/secure/translations/delete")
    RedirectView deleteTranslation(@RequestParam String language) {
          return translationService.delete(language);
    }
}

