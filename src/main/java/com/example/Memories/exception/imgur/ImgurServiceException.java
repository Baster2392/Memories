package com.example.Memories.exception.imgur;

import com.nimbusds.oauth2.sdk.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImgurServiceException extends RuntimeException{
    private String responseBody;
    public ImgurServiceException(Exception e) {
        super(e);
    }

    public ImgurServiceException(String message) {
        super(message);
    }

    public ImgurServiceException() {
        super("Error during communicating with Imgur API.");
    }
}
