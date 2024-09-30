package com.example.Memories.controller;

import com.example.Memories.model.ImgurImage;
import com.example.Memories.model.User;
import com.example.Memories.service.ImgurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/photos")
public class PhotoController {
    private final ImgurService imgurService;
    private final HttpSession httpSession;

    public PhotoController(ImgurService imgurService, HttpSession httpSession) {
        this.imgurService = imgurService;
        this.httpSession = httpSession;
    }

    @GetMapping
    @RequestMapping("/link")
    ResponseEntity<String> getLink() {
        return ResponseEntity.ok("<a href=\"https://imgur.com/d2s55po\"><img src=\"https://i.imgur.com/d2s55po.jpg\" title=\"source: imgur.com\" /></a>");
    }

    @GetMapping(value = "/all", produces = MediaType.TEXT_HTML_VALUE)
    public String getAllAccountImages(Model model) {
        User user = (User) httpSession.getAttribute("user");
        List<ImgurImage> imgurImages;
        try {
            imgurImages = imgurService.getImages(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("images", imgurImages);
        return "allAccountImages";
    }
}
