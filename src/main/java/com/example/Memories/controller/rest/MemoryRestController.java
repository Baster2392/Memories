package com.example.Memories.controller.rest;

import com.example.Memories.exception.imgur.ImgurServiceException;
import com.example.Memories.model.Memory;
import com.example.Memories.model.User;
import com.example.Memories.service.MemoryService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/api/memories")
public class MemoryRestController {
    private final HttpSession httpSession;
    private final MemoryService memoryService;
    private static final Logger logger = LoggerFactory.getLogger(MemoryService.class);

    public MemoryRestController(HttpSession httpSession, MemoryService memoryService) {
        this.httpSession = httpSession;
        this.memoryService = memoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMemories() {
        User user = (User) httpSession.getAttribute("user");
        if (user.getImgurToken() == null) {
            logger.info("Imgur token is null.");
            return ResponseEntity.badRequest().build();
        }

        List<Memory> memories = memoryService.findAll(user);
        return ResponseEntity.ok(memories);
    }

    @PostMapping()
    public ResponseEntity<?> postMemory(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("start_date") String startDate,
            @RequestParam("end_date") String endDate,
            @RequestParam("files") List<MultipartFile> files
    ) {
        User user = (User) httpSession.getAttribute("user");
        user = memoryService.saveMemory(user, title, description, startDate, endDate, files);
        httpSession.setAttribute("user", user);
        return ResponseEntity.ok("Memory saved.");
    }
}
