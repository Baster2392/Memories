package com.example.Memories.service;

import com.example.Memories.model.User;
import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.repositories.ImgurTokenRepository;
import com.example.Memories.model.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImgurTokenRepository imgurTokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository, ImgurTokenRepository imgurTokenRepository) {
        this.userRepository = repository;
        this.imgurTokenRepository = imgurTokenRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void createNewUser(User user) {
        logger.info("Saving user...");
        userRepository.save(user);
        logger.info("User saved.");
    }

    public void handleGoogleLogin(OAuth2User oAuth2User) {
        logger.info("Logging user to app...");

        String email = oAuth2User.getAttribute("email");
        String givenName = oAuth2User.getAttribute("given_name");

        if (userRepository.findByEmail(email).isEmpty()) {
            handleGoogleRegistration(email, givenName);
        }

        logger.info("User logged in.");
    }

    private void handleGoogleRegistration(String email, String givenName) {
        logger.info("Registering user...");

        User newUser = new User(givenName, email, null, new ArrayList<>());
        userRepository.save(newUser);

        logger.info("User registered.");
    }

    @Transactional
    public void handleImgurLogin(User user, String accessToken, String refreshToken, Integer expiresIn, String username) {
        logger.info("Logging user to imgur...");

        ImgurToken imgurToken = new ImgurToken(accessToken, refreshToken, Long.valueOf(expiresIn) * 1000 + System.currentTimeMillis(), username);
        user.setImgurToken(imgurToken);
        userRepository.save(user);

        logger.info("User logged to imgur.");
    }

    public List<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateUserImgurToken(User user, ImgurToken imgurToken) {
        imgurTokenRepository.deleteById(user.getImgurToken().getTokenId());
        user.setImgurToken(imgurToken);
    }

    @Transactional
    public User deleteImgurToken(User user) {
        logger.info("Deleting imgur token...");

        imgurTokenRepository.deleteById(user.getImgurToken().getTokenId());
        user.setImgurToken(null);
        User updatedUser = userRepository.save(user);

        logger.info("Imgur token deleted.");
        return updatedUser;
    }
}
