package com.rigoberto.console.controllers;

import com.rigoberto.console.utils.Translator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locale")
public class LocaleController extends BaseController {
    public LocaleController() {
        super(LoggerFactory.getLogger(LocaleController.class));
    }

    @Operation(summary = "Get a locale by its id")
    @GetMapping("/{id}")
    public String getById(@Parameter(description = "id of locale to be searched") @PathVariable Integer id) throws Exception {
        if(id > 0) {
            return Translator.toLocale("locale_info1", new String[]{id.toString()});
        }

        throw new Exception(Translator.toLocale("locale_error1"));
    }
}
