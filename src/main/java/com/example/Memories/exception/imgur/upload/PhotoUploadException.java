package com.example.Memories.exception.imgur.upload;

import com.example.Memories.exception.imgur.ImgurServiceException;

public class PhotoUploadException extends ImgurServiceException {
    public PhotoUploadException(String message) {
        super(message);
    }
}
