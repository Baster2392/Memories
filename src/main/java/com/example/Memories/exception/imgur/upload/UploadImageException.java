package com.example.Memories.exception.imgur.upload;

import com.example.Memories.exception.imgur.ImgurServiceException;

public class UploadImageException extends ImgurServiceException {
    public UploadImageException(Exception e) {
        super(e);
    }

    public UploadImageException(String message) {
        super("Error during uploading image: " + message);
    }

    public UploadImageException() {
        super("Error during uploading image.");
    }
}
