package com.example.Memories.service;

import com.example.Memories.exception.imgur.ImgurServiceException;
import com.example.Memories.exception.imgur.upload.*;
import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.User;
import com.example.Memories.model.response.ImgurAlbumCreationResponse;
import com.example.Memories.model.response.ImgurImage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    private final static Logger logger = LoggerFactory.getLogger(ImgurService.class);

    public ImgurService(WebClient.Builder webClientBuilder, UserService userService) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.imgur.com")
                .build();
        this.userService = userService;
    }

    private boolean isImgurTokenExpired(User user) {
        boolean isExpired = user.getImgurToken().getExpiresIn() <= System.currentTimeMillis();
        System.out.println("Comparing token: " + user.getImgurToken().getExpiresIn() + " <= " + System.currentTimeMillis());
        if (isExpired) {
            logger.info("Imgur token expired.");
            return true;
        }
        logger.info("Imgur token isn't expired.");
        return false;
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
        logger.info("Sending imgur token update request...");
        Map<String, Object> responseMap = response.block();
        logger.info("New imgur token obtained.");
        String accessToken = (String) responseMap.get("access_token");
        String refreshToken = (String) responseMap.get("refresh_token");
        Long expiresIn = (long) (3600 * 1000);
        Long currentTime = System.currentTimeMillis();
        System.out.println(expiresIn);
        System.out.println(currentTime);
        expiresIn = expiresIn + currentTime;
        System.out.println(expiresIn);
        String accountUsername = (String) responseMap.get("account_username");

        // Save new token
        System.out.println(user.getImgurToken().getAccessToken());
        System.out.println(user.getImgurToken().getExpiresIn());
        ImgurToken newToken = new ImgurToken(accessToken, refreshToken, expiresIn, accountUsername);
        userService.updateUserImgurToken(user, newToken);
        System.out.println(user.getImgurToken().getAccessToken());
        System.out.println(user.getImgurToken().getExpiresIn());
    }

    public List<ImgurImage> getImages(User user) throws JsonProcessingException {
        if (isImgurTokenExpired(user)) {
            updateImgurToken(user);
        }

        Mono<String> responseMono = webClient.get()
                .uri("/3/account/" + user.getImgurToken().getImgurName() + "/images")
                .header("Authorization", "Bearer " + user.getImgurToken().getAccessToken())
                .retrieve()
                .onStatus(HttpStatusCode::isError, getExceptionFunction(new GetImageException("Error during getting images.")))
                .bodyToMono(String.class);

        logger.info("Sending request for user images.");
        String response = responseMono.block();
        logger.info("Response obtained.");
        return (List<ImgurImage>) extractDataToList(response, ImgurImage.class);
    }

    private Object extractData(String response, Class<?> dataClass) {
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(response);
            return objectMapper.treeToValue(rootNode.get("data"), dataClass);
        } catch (JsonProcessingException e) {
            logger.error("Exception during parsing json.");
            throw new UnprocessableResponseException("Response: " + response);
        }
    }

    private List<?> extractDataToList(String response, Class<?> dataClass) {
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            logger.error("Exception during parsing json.");
            throw new UnprocessableResponseException("Response: " + response);
        }
        JsonNode dataNode = rootNode.get("data");

        return StreamSupport.stream(dataNode.spliterator(), false)
                .map(node -> {
                    try {
                        return objectMapper.treeToValue(node, dataClass);
                    } catch (Exception e) {
                        logger.error("Exception during parsing json.");
                        throw new UnprocessableResponseException("Response node: " + node);
                    }
                })
                .collect(Collectors.toList());
    }

    public ImgurImage uploadImage(User user, MultipartFile image, String title, String description) throws IOException {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("image", new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        });
        map.add("type", "image");
        map.add("title", title);
        map.add("description", description);

        Mono<String> responseMono = webClient.post()
                .uri("https://api.imgur.com/3/image")
                .header("Authorization", "Bearer " + user.getImgurToken().getAccessToken())
                .body(BodyInserters.fromMultipartData(map))
                .retrieve()
                .onStatus(HttpStatusCode::isError, getExceptionFunction(new UploadImageException()))
                .bodyToMono(String.class);

        logger.info("Sending request for image upload...");
        String response = responseMono.block();
        logger.info("Response obtained.");
        return (ImgurImage) extractData(response, ImgurImage.class);
    }

    public ImgurAlbumCreationResponse createAlbum(User user, String title, String description, List<ImgurImage> imgurImages) {
        List<String> imagesIds = imgurImages.stream()
                .map(ImgurImage::getId)
                .toList();
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.addAll("ids[]", imagesIds);
        map.add("title", title);
        map.add("description", description);
        map.add("cover", imagesIds.getFirst()); // TODO: Add cover setting

        Mono<String> responseMono = webClient.post()
                .uri("https://api.imgur.com/3/album")
                .header("Authorization", "Bearer " + user.getImgurToken().getAccessToken())
                .body(BodyInserters.fromMultipartData(map))
                .retrieve()
                .onStatus(HttpStatusCode::isError, getExceptionFunction(new AlbumCreationException("Error during album creation")))
                .bodyToMono(String.class);

        logger.info("Sending request for album creation.");
        String response = responseMono.block();
        logger.info("Request obtained.");
        return (ImgurAlbumCreationResponse) extractData(response, ImgurAlbumCreationResponse.class);
    }

    private static Function<ClientResponse, Mono<? extends Throwable>> getExceptionFunction(ImgurServiceException e) {
        return clientResponse -> clientResponse.bodyToMono(String.class)
                .doOnNext(errorBody -> {
                    logger.error("Error response body: " + errorBody);
                    e.setResponseBody(errorBody);
                })
                .flatMap(errorBody -> Mono.error(e));
    }

    public void addImagesToAlbum(User user, List<ImgurImage> imgurImages, ImgurAlbumCreationResponse imgurAlbumCreationResponse) {
        List<String> imagesIds = imgurImages.stream()
                .map(ImgurImage::getId)
                .toList();
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.addAll("ids[]", imagesIds);

        Mono<String> responseMono = webClient.post()
                .uri("https://api.imgur.com/3/album/" + imgurAlbumCreationResponse.getDeletehash())
                .header("Authorization", "Bearer " + user.getImgurToken().getAccessToken())
                .body(BodyInserters.fromMultipartData(map))
                .retrieve()
                .onStatus(HttpStatusCode::isError, getExceptionFunction(new AddImagesToAlbumException()))
                .bodyToMono(String.class);

        logger.info("Sending request for adding images to album.");
        responseMono.block();
        logger.info("Response obtained.");
    }
}

