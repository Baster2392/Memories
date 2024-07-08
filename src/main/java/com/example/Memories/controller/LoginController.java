package com.example.Memories.controller;

import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.User;
import com.example.Memories.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
    @Value("${Memories.clientId}")
    private String clientId;
    @Value("${Memories.redirectUrl}")
    private String redirectUrl;
    private final UserService service;
    private final HttpSession httpSession;

    public LoginController(UserService service, HttpSession httpSession) {
        this.service = service;
        this.httpSession = httpSession;
    }

    @GetMapping("/imgur/connect")
    public RedirectView connectToImgur() {
       String tokenUrl = "https://api.imgur.com/oauth2/authorize?client_id=" +
                clientId + "&response_type=token&state=RUNNING";
        return new RedirectView(tokenUrl);
    }

    @GetMapping(value = "imgur/callback", produces = MediaType.TEXT_HTML_VALUE)
    public String getAuthToken(Model model) {
        model.addAttribute("redirectUrl", redirectUrl);
        return "accessTokenCallback";
    }

    @GetMapping(value = "/saveToken")
    public ResponseEntity<?> saveAuthToken(
            @RequestParam("access_token") String accessToken,
            @RequestParam("refresh_token") String refreshToken,
            @RequestParam("expires_in") Integer expiresIn,
            @RequestParam("account_username") String username
    ) {
        User user = (User) httpSession.getAttribute("user");
        service.handleImgurLogin(user, accessToken, refreshToken, expiresIn, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/logged", produces = MediaType.TEXT_HTML_VALUE)
    public String logged() {
        return "loggedToImgur";
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user", user);

        ImgurToken imgurToken = user.getImgurToken();
        if (imgurToken == null) {
            imgurToken = new ImgurToken();
        }

        model.addAttribute("imgurToken", imgurToken);
        return "home";
    }

    @GetMapping(value = "/login_callback")
    public RedirectView loginCallback(Authentication authentication) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        service.handleGoogleLogin(user);
        return new RedirectView("/");
    }
}
