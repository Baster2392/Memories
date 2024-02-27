package com.example.Memories.controller;

import com.example.Memories.model.Photo;
import com.example.Memories.model.PhotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @GetMapping
    ResponseEntity<List<Photo>> readAll() {
        return ResponseEntity.ok(photoRepository.findAll());
    }

    @GetMapping
    @RequestMapping("/link")
    ResponseEntity<String> getLink() {
        return ResponseEntity.ok("<a href=\"https://imgur.com/d2s55po\"><img src=\"https://i.imgur.com/d2s55po.jpg\" title=\"source: imgur.com\" /></a>");
    }
}
