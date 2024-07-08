package com.example.Memories.service;

import com.example.Memories.model.User;
import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
        repository.save(user);
    }

    public void handleGoogleLogin(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String givenName = oAuth2User.getAttribute("given_name");

        if (repository.findByEmail(email).isEmpty()) {
            System.out.println(repository.findAll());
            handleGoogleRegistration(email, givenName);
        }
    }

    private void handleGoogleRegistration(String email, String givenName) {
        User newUser = new User(givenName, email, null, new ArrayList<>());
        repository.save(newUser);
    }

    @Transactional
    public void handleImgurLogin(User user, String accessToken, String refreshToken, Integer expiresIn, String username) {
        ImgurToken imgurToken = new ImgurToken(accessToken, refreshToken, expiresIn + System.currentTimeMillis(), username);
        user.setImgurToken(imgurToken);
        repository.save(user);
    }

    public List<User> findUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}
