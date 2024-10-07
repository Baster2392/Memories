package com.example.Memories.model.repositories;

import com.example.Memories.model.Memory;
import com.example.Memories.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository {
    Memory save(Memory memory);
    List<Memory> findAll();
    List<Memory> findByUser(User user);
}
