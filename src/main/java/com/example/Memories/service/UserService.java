package com.example.Memories.service;

import com.example.Memories.model.User;
import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
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

    public void handleNewLogin(String accessToken, String refreshToken, Integer expiresIn, String username) {
        ImgurToken token = new ImgurToken(null, null, accessToken, refreshToken, expiresIn);
        List<User> foundUser = repository.findByUsername(username);
        if (foundUser.isEmpty()) {
            User newUser = new User(username, token, new ArrayList<>());
            repository.save(newUser);
        } else {
            User user = foundUser.getFirst();
            user.refreshImgurToken(token);
        }
    }

    public void createNewUser(User user) {
        repository.save(user);
    }
}
