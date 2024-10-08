package com.example.Memories.exception.memories.upload;

import com.example.Memories.exception.memories.MemoriesAppException;

public class MediaFileParsingException extends IncorrectDataException {
    public MediaFileParsingException(Exception e) {
        super(e);
    }
}
