package edu.java.scrapper.controller;

import edu.java.scrapper.dto.LinkDto;
import edu.java.scrapper.dto.UserDto;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrackController {
    private final LinkService linkService;
    private final UserService userService;

    @PostMapping("/track")
    public ResponseEntity<?> addTrack(
        @RequestParam("chatId") Long chatId,
        @RequestParam("service") String serviceUrl
    ) {
        UserDto user = userService.login(chatId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        linkService.add(user.getId(), serviceUrl);
        return ResponseEntity.ok("Сервис " + serviceUrl + " добавлен в отслеживание");
    }

    @DeleteMapping("/untrack")
    public ResponseEntity<?> removeTrack(
        @RequestParam("chatId") Long chatId,
        @RequestParam("service") String serviceUrl
    ) {
        UserDto user = userService.login(chatId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        boolean res = linkService.remove(user.getId(), serviceUrl);
        if (!res) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Сервис " + serviceUrl + " больше не отслеживается");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTracks(
        @RequestParam("chatId") Long chatId
    ) {
        UserDto user = userService.login(chatId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<LinkDto> tracks = (List<LinkDto>) linkService.listByChatId(user.getId());
        if (tracks.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        return ResponseEntity.ok(tracks.stream().map(LinkDto::getUrl));
    }
}
