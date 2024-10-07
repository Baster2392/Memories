package com.example.Memories.controller.mvc;

import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.User;
import com.example.Memories.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
    @Value("${Memories.clientId}")
    private String clientId;
    @Value("${Memories.redirectUrl}")
    private String redirectUrl;
    private final UserService userService;
    private final HttpSession httpSession;

    public LoginController(UserService service, HttpSession httpSession) {
        this.userService = service;
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
        return "login/accessTokenCallback";
    }

    @GetMapping(value = "/logged", produces = MediaType.TEXT_HTML_VALUE)
    public RedirectView logged() {
        return new RedirectView("/");
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
        userService.handleGoogleLogin(user);
        return new RedirectView("/");
    }
}
