package com.example.Memories.service;

import com.example.Memories.model.ImgurImage;
import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ImgurService {

    private final WebClient webClient;
    private final UserService userService;
    @Value("${Memories.clientId}")
    private String clientId;
    @Value("${Memories.clientSecret}")
    private String clientSecret;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ImgurService(WebClient.Builder webClientBuilder, UserService userService) {
        this.webClient = webClientBuilder.baseUrl("https://api.imgur.com").build();
        this.userService = userService;
    }

    private boolean isImgurTokenExpired(User user) {
        return user.getImgurToken().getExpiresIn() <= System.currentTimeMillis();
    }

    private void updateImgurToken(User user) {
        // Send request for new access token
        ImgurToken imgurToken = user.getImgurToken();
        Mono<Map> response = webClient.post()
                .uri("/oauth2/token")
                .bodyValue(Map.of(
                        "refresh_token", imgurToken.getRefreshToken(),
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "grant_type", "refresh_token"
                ))
                .retrieve()
                .bodyToMono(Map.class);

        // Extract data from response
        Map<String, Object> responseMap = response.block();
        String accessToken = (String) responseMap.get("access_token");
        String refreshToken = (String) responseMap.get("refresh_token");
        Long expiresIn = Long.valueOf((Integer) responseMap.get("expires_in")) * 1000 + System.currentTimeMillis();
        String accountUsername = (String) responseMap.get("account_username");

        // Save new token
        ImgurToken newToken = new ImgurToken(accessToken, refreshToken, expiresIn, accountUsername);
        newToken.setUser(user);
        userService.updateUserImgurToken(newToken);
    }

    public List<ImgurImage> getImages(User user) throws JsonProcessingException {
        if (isImgurTokenExpired(user)) {
            updateImgurToken(user);
        }

        Mono<String> responseMono = webClient.get()
                .uri("/3/account/" + user.getImgurToken().getImgurName() + "/images")
                .header("Authorization", "Bearer " + user.getImgurToken().getAccessToken())
                .retrieve()
                .bodyToMono(String.class);

        String jsonResponse = responseMono.block();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode dataNode = rootNode.get("data");

        List<ImgurImage> images = StreamSupport.stream(dataNode.spliterator(), false)
                .map(node -> {
                    try {
                        return objectMapper.treeToValue(node, ImgurImage.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return images;
    }
}

