package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ramazanmamyrbek.kazinsightmonolith.exception.FileNotSupportedException;

import java.nio.file.FileAlreadyExistsException;
import java.util.Locale;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model, Locale locale) {
        model.addAttribute("error", messageSource.getMessage(exception.getMessage(), new Object[0],
                exception.getMessage(), locale));
        return "errors/404";
    }

    @ExceptionHandler(FileNotSupportedException.class)
    public String handleFileNotSupportedException(FileNotSupportedException exception, Model model, Locale locale) {
        model.addAttribute("errors", messageSource.getMessage(exception.getMessage(), new Object[0],
                exception.getMessage(), locale));
        return "admin/places/new_place";
    }
}
