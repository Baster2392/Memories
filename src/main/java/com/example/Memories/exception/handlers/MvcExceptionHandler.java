package com.example.Memories.exception.handlers;

import com.example.Memories.exception.imgur.ImgurServiceException;
import com.example.Memories.exception.imgur.upload.UnprocessableResponseException;
import com.example.Memories.exception.memories.MemoriesAppException;
import com.example.Memories.exception.memories.upload.IncorrectDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class MvcExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MvcExceptionHandler.class);

    @ExceptionHandler(ImgurServiceException.class)
    public String handleImgurServiceException(ImgurServiceException e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(WebClientException.class)
    public String handleWebClientException(WebClientException e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(UnprocessableResponseException.class)
    public String handleUnprocessableResponseException(UnprocessableResponseException e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(IncorrectDataException.class)
    public String handleIncorrectDataException(IncorrectDataException e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(MemoriesAppException.class)
    public String handleMemoriesAppException(MemoriesAppException e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }
}
