package com.example.Memories.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CallbackController {
    @Value("${Memories.clientId}")
    private String clientId;

    @GetMapping("/token")
    public RedirectView redirectToImgur() {
       String tokenUrl = "https://api.imgur.com/oauth2/authorize?client_id=" +
                clientId + "&response_type=token&state=RUNNING";

        return new RedirectView(tokenUrl);
    }

    @GetMapping(value = "/callback", produces = MediaType.TEXT_HTML_VALUE)
    public String getAuthToken() {
        return "accessTokenCallback";
    }

    @GetMapping(value = "/saveToken")
    public ResponseEntity<String> saveAuthToken(
            @RequestParam("access_token") String accessToken,
            @RequestParam("refresh_token") String refreshToken,
            @RequestParam("expires_in") Integer expiresIn,
            @RequestParam("account_username") String username
    ) {
        // TODO: saving tokens in db

        System.out.println(accessToken);
        System.out.println(refreshToken);
        System.out.println(expiresIn);
        System.out.println(username);
        return ResponseEntity.ok(accessToken);
    }
}
