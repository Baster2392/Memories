package com.example.Memories.security;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OAuth2ClientService {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public OAuth2ClientService(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    public OAuth2AuthorizedClient getAuthorizedClient(String clientRegistrationId, String principalName) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
                .principal(principalName)
                .build();

        OAuth2AuthorizedClient authorizedClient;
        try {
            authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        } catch (OAuth2AuthenticationException e) {
            // Handle the exception
            Logger.getGlobal().warning(e.getMessage());
            return null;
        }

        return authorizedClient;
    }
}
