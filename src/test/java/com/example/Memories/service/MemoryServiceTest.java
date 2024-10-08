package com.example.Memories.service;

import com.example.Memories.model.repositories.MemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class MemoryServiceTest {
    @InjectMocks
    private MemoryService memoryService;

    @Mock
    private MemoryRepository memoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
    }

    @Test
    void saveMemory_DateParseException() {

    }
}