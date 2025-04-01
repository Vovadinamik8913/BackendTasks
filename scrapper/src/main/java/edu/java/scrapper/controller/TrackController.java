package edu.java.scrapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {
    @PostMapping("/track")
    public ResponseEntity<?> addTrack(

        @RequestParam("service") String serviceUrl
    ) {
        return ResponseEntity.ok("Сервис " + serviceUrl + " добавлен в отслеживание");
    }

    @DeleteMapping("/untrack")
    public ResponseEntity<?> removeTrack(
        @RequestParam("service") Long serviceUrl
    ) {
        return ResponseEntity.ok("Сервис " + serviceUrl + " больше не отслеживается");
    }

    @GetMapping("/list")
    public ResponseEntity<?> removeTrack() {
        return ResponseEntity.ok("Список отслеживаемых сайтов");
    }
}
