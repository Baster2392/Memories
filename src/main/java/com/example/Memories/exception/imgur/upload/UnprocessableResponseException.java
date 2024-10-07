package com.example.Memories.exception.imgur.upload;

import com.example.Memories.exception.imgur.ImgurServiceException;

public class UnprocessableResponseException extends ImgurServiceException {
    public UnprocessableResponseException(Exception e) {
        super(e);

    }

    public UnprocessableResponseException(String message) {
        super(message);
    }
}
