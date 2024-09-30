package com.example.Memories.service;

import com.example.Memories.model.User;
import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.repositories.ImgurTokenRepository;
import com.example.Memories.model.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImgurTokenRepository imgurTokenRepository;

    public UserService(UserRepository repository, ImgurTokenRepository imgurTokenRepository) {
        this.userRepository = repository;
        this.imgurTokenRepository = imgurTokenRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void createNewUser(User user) {
        userRepository.save(user);
    }

    public void handleGoogleLogin(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String givenName = oAuth2User.getAttribute("given_name");

        if (userRepository.findByEmail(email).isEmpty()) {
            System.out.println(userRepository.findAll());
            handleGoogleRegistration(email, givenName);
        }
    }

    private void handleGoogleRegistration(String email, String givenName) {
        User newUser = new User(givenName, email, null, new ArrayList<>());
        userRepository.save(newUser);
    }

    @Transactional
    public void handleImgurLogin(User user, String accessToken, String refreshToken, Integer expiresIn, String username) {
        ImgurToken imgurToken = new ImgurToken(accessToken, refreshToken, expiresIn + System.currentTimeMillis(), username);
        user.setImgurToken(imgurToken);
        userRepository.save(user);
    }

    public List<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUserImgurToken(ImgurToken imgurToken) {
        imgurTokenRepository.save(imgurToken);
    }
}
