package com.example.Memories.exception.memories;

public class MemoriesAppException extends RuntimeException {
    private String responseBody;
    public MemoriesAppException(Exception e) {
        super(e);
    }

    public MemoriesAppException(String message) {
        super(message);
    }

    public MemoriesAppException() {
        super("Error during Memories runtime.");
    }
}
