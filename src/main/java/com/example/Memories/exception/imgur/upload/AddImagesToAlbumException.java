package com.example.Memories.exception.imgur.upload;

import com.example.Memories.exception.imgur.ImgurServiceException;

public class AddImagesToAlbumException extends ImgurServiceException {
    public AddImagesToAlbumException(String message) {
        super(message);
    }

    public AddImagesToAlbumException() {
        super("Error during adding images to album.");
    }
}
