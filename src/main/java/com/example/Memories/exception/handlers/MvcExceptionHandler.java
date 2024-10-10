package com.example.Memories.exception.handlers;

import com.example.Memories.exception.imgur.ImgurServiceException;
import com.example.Memories.exception.imgur.upload.UnprocessableResponseException;
import com.example.Memories.exception.memories.MemoriesAppException;
import com.example.Memories.exception.memories.upload.WrongDateFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(basePackages = "com.example.Memories.controller.mvc")
public class MvcExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MvcExceptionHandler.class);

    @ExceptionHandler(ImgurServiceException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public String handleImgurServiceException(ImgurServiceException e, RedirectAttributes redirectAttributes) {
        logger.error(e.getClass() + ": " + e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(WrongDateFormatException.class)
    public String handleWrongDateFormatException(WrongDateFormatException e, RedirectAttributes redirectAttributes) {
        logger.error(e.getClass() + ": " + e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(WebClientException.class)
    public String handleWebClientException(WebClientException e, RedirectAttributes redirectAttributes) {
        logger.error(e.getClass() + ": " + e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(UnprocessableResponseException.class)
    public String handleUnprocessableResponseException(UnprocessableResponseException e, RedirectAttributes redirectAttributes) {
        logger.error(e.getClass() + ": " + e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(MemoriesAppException.class)
    public String handleMemoriesAppException(MemoriesAppException e, RedirectAttributes redirectAttributes) {
        logger.error(e.getClass() + ": " + e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/errorPage";
    }
}
