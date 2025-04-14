package edu.java.scrapper.controller;

import edu.java.scrapper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/start")
    public ResponseEntity<?> addTrack(
        @RequestParam("chatId") Long chatId
    ) {
        userService.register(chatId);
        return ResponseEntity.ok("Вы зарегистрированы!! Добро пожаловать");
    }
}
