package com.example.Memories.controller.rest;

import com.example.Memories.model.User;
import com.example.Memories.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createNewUser(user);
            return ResponseEntity.ok("User saved");
        } catch (EntityExistsException e) {
            Logger.getGlobal().warning("Failed to create user: " + e.getMessage());
            return ResponseEntity.unprocessableEntity().body("User already exists.");
        } catch (Exception e) {
            Logger.getGlobal().severe("Failed to create user: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
