package com.example.Memories.controller.rest;

import com.example.Memories.model.User;
import com.example.Memories.service.UserService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class LoginRestController {
    @Value("${Memories.clientId}")
    private String clientId;
    @Value("${Memories.redirectUrl}")
    private String redirectUrl;
    private final UserService userService;
    private final HttpSession httpSession;

    public LoginRestController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @GetMapping(value = "/imgur/saveToken")
    public ResponseEntity<?> saveAuthToken(
            @Valid @Nonnull @RequestParam("access_token") String accessToken,
            @Valid @Nonnull @RequestParam("refresh_token") String refreshToken,
            @Valid @Nonnull @RequestParam("expires_in") Integer expiresIn,
            @Valid @Nonnull @RequestParam("account_username") String username
    ) {
        User user = (User) httpSession.getAttribute("user");
        userService.handleImgurLogin(user, accessToken, refreshToken, expiresIn, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/imgur/disconnect")
    public ResponseEntity<?> disconnectFromImgur() {
        User user = (User) httpSession.getAttribute("user");
        if (user.getImgurToken() == null) {
            Logger.getGlobal().warning("Exception: Imgur token is null. User: " + user.getId());
            return ResponseEntity.notFound().build();
        }
        user = userService.deleteImgurToken(user);
        httpSession.setAttribute("user", user);
        return ResponseEntity.ok("Disconnected from Imgur.");
    }
}
