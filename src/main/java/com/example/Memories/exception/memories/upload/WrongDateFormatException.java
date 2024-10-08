package com.example.Memories.exception.memories.upload;

import java.text.ParseException;

public class WrongDateFormatException extends IncorrectDataException {

    public WrongDateFormatException(ParseException e) {
        super(e);
    }
}
