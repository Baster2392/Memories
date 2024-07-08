package com.example.Memories.security;

import com.example.Memories.model.User;
import com.example.Memories.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final OAuth2ClientService oAuth2ClientService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private UserService userService;

    public CustomOidcUserService(OAuth2ClientService oAuth2ClientService) {
        this.oAuth2ClientService = oAuth2ClientService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Add user data to session data
        String email = oidcUser.getEmail();
        List<User> optionalUser = userService.findUserByEmail(email);
        if (!optionalUser.isEmpty()) {
            User user = optionalUser.getFirst();
            httpSession.setAttribute("user", user);
        }
        else {
            String name = oidcUser.getGivenName();
            User newUser = new User(name, email, null, new ArrayList<>());
            userService.createNewUser(newUser);
            httpSession.setAttribute("user", newUser);
        }

        return oidcUser;
    }
}

