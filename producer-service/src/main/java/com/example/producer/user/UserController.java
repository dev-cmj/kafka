package com.example.producer.user;

import com.example.common.entity.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/process")
    public ResponseEntity<Void> processUser(@RequestBody UserMessage userMessage) {
        userService.processUser(userMessage);
        return ResponseEntity.ok().build();
    }
}