package com.example.Memories.controller.mvc;

import com.example.Memories.model.response.ImgurImage;
import com.example.Memories.model.User;
import com.example.Memories.service.ImgurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

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
        if (user.getImgurToken() == null) {
            return "redirect:/";
        }

        List<ImgurImage> imgurImages;
        try {
            imgurImages = imgurService.getImages(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("images", imgurImages);
        return "allAccountImages";
    }

    @PostMapping(value = "/upload", produces = MediaType.TEXT_HTML_VALUE)
    public String upload(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("start_date") String startDate,
            @RequestParam("end_date") String endDate,
            @RequestParam("files") List<MultipartFile> files
            ) {
        User user = (User) httpSession.getAttribute("user");
        ImgurImage response = imgurService.uploadImage(user, files.getFirst(), "", "");
        return "uploadMemory";
    }
}
