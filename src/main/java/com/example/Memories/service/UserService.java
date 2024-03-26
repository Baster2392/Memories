package com.example.Memories.service;

import com.example.Memories.model.User;
import com.example.Memories.model.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void createNewUser(User user) {
        if (!repository.findByUsername(user.getUsername()).isEmpty()) {
            throw new EntityExistsException("User already with this username already exist.");
        }

        user.setMemories(new ArrayList<>());
        repository.save(user);
    }
}
