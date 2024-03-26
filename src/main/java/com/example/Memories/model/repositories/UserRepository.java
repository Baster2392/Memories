package com.example.Memories.model.repositories;

import com.example.Memories.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User save(User user);
    List<User> findByUsername(String username);
}
