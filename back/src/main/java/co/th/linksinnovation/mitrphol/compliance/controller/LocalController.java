/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.controller;

import co.th.linksinnovation.mitrphol.compliance.model.Language;
import co.th.linksinnovation.mitrphol.compliance.repository.LocaleRespository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jirawong
 */
@RestController
public class LocalController {

    @Autowired
    private LocaleRespository localeRespository;

    @Cacheable(value = "locales")
    @GetMapping("/public/locales")
    public List<Language> get() {
        return localeRespository.findAll();
    }

    @Cacheable(value = "locale")
    @GetMapping("/public/locales/{lang}")
    public String getLocale(@PathVariable String lang) throws IOException {
        final String jsonFile = "/mnt/locales/" + lang + ".json";
        return new String(Files.readAllBytes(Paths.get(jsonFile)), StandardCharsets.UTF_8);
    }

}
