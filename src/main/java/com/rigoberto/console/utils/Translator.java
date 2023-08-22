package com.rigoberto.console.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String msgCode) {
        return messageSource.getMessage(msgCode, null, getLocale());
    }

    public static String toLocale(String msgCode, String [] args) {
        return messageSource.getMessage(msgCode, args, getLocale());
    }

    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

}
