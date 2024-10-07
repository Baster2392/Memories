package com.example.Memories.adapter;

import com.example.Memories.model.Memory;
import com.example.Memories.model.repositories.MemoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlMemoryRepository extends MemoryRepository, JpaRepository<Memory, Long> {
}
