package com.example.Memories.controller;

import com.example.Memories.service.UserService;
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
public class ImgurLoginController {
    @Value("${Memories.clientId}")
    private String clientId;
    @Value("${Memories.redirectUrl}")
    private String redirectUrl;
    private final UserService service;

    public ImgurLoginController(UserService service) {
        this.service = service;
    }

    @GetMapping("/imgur/login")
    public RedirectView redirectToImgur() {
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
        System.out.println(accessToken);
        System.out.println(refreshToken);
        System.out.println(expiresIn);
        System.out.println(username);

        service.handleNewLogin(accessToken, refreshToken, expiresIn, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/logged", produces = MediaType.TEXT_HTML_VALUE)
    public String logged() {
        return "loggedToImgur";
    }

    @GetMapping(value = "/")
    public String home(Model model, Authentication authentication) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        model.addAttribute("userName", user.getAttribute("given_name"));
        return "home";
    }
}
