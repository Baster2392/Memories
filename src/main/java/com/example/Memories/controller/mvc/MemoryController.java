package com.example.Memories.controller.mvc;

import com.example.Memories.service.MemoryService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memories")
public class MemoryController {
    private final HttpSession httpSession;
    private final MemoryService memoryService;
    private static final Logger logger = LoggerFactory.getLogger(MemoryService.class);

    public MemoryController(HttpSession httpSession, MemoryService memoryService) {
        this.httpSession = httpSession;
        this.memoryService = memoryService;
    }

    @GetMapping("/upload")
    public String uploadMemory() {
        return "uploadMemory";
    }
}
