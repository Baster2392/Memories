package com.example.Memories.exception.handlers;

import com.example.Memories.exception.imgur.ImgurServiceException;
import com.example.Memories.exception.imgur.upload.UnprocessableResponseException;
import com.example.Memories.exception.memories.MemoriesAppException;
import com.example.Memories.exception.memories.upload.IncorrectDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(ImgurServiceException.class)
    public ResponseEntity<String> handleImgurServiceException(ImgurServiceException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<String> handleWebClientException(WebClientException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(UnprocessableResponseException.class)
    public ResponseEntity<String> handleUnprocessableResponseException(UnprocessableResponseException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<String> handleIncorrectDataException(IncorrectDataException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }
}
