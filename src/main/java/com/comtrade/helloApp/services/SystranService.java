package com.comtrade.helloApp.services;

import com.comtrade.helloApp.models.SystranOutputs;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class SystranService {
    private final String uri = "https://api-translate.systran.net";

    public String translate(String language) throws Exception {
        String input = "Hello World";
        String source = "en";

        if (language == null || language.isEmpty()) {
            language = "en";
        }

        // Systran throws error if srouce and language are the same
        if (source.equals(language)) {
            return input;
        }

        try {
            String key = "8885ea3a-23a5-4230-9a22-99ea30031d6f";
            SystranOutputs result = new RestTemplate().postForObject(
                    uri + "/translation/text/translate?input=" + input + "&source=" + source + "&target=" + language + "&key=" + key,
                    null,
                    SystranOutputs.class
            );
            return result.getOutputs()[0].getOutput();
        } catch (Exception e) {
            throw new Exception("No translation found");
        }

    }

}