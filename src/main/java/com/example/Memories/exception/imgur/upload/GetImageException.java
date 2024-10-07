package com.example.Memories.exception.imgur.upload;

import com.example.Memories.exception.imgur.ImgurServiceException;

public class GetImageException extends ImgurServiceException {
    public GetImageException(Exception message) {
        super(message);
    }

    public GetImageException(String message) {
        super(message);

    }
}
