package com.example.Memories.controller.mvc;

import com.example.Memories.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/errorPage")
public class ExceptionController {
    private final UserService userService;

    public ExceptionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public String errorPage(Model model) {
        String errorMessage = (String) model.getAttribute("errorMessage");
        model.addAttribute("message", Objects.requireNonNullElse(errorMessage, "No error"));
        return "errors/errorPage";
    }
}
