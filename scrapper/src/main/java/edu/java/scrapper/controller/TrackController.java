package edu.java.scrapper.controller;

import edu.java.scrapper.dto.Track;
import edu.java.scrapper.dto.User;
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
        User user = userService.login(chatId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        linkService.add(user.getChatId(), serviceUrl);
        return ResponseEntity.ok("Сервис " + serviceUrl + " добавлен в отслеживание");
    }

    @DeleteMapping("/untrack")
    public ResponseEntity<?> removeTrack(
        @RequestParam("chatId") Long chatId,
        @RequestParam("service") String serviceUrl
    ) {
        User user = userService.login(chatId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        boolean res = linkService.remove(user.getChatId(), serviceUrl);
        if (!res) {
            System.out.println("hehe");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Сервис " + serviceUrl + " больше не отслеживается");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTracks(
        @RequestParam("chatId") Long chatId
    ) {
        User user = userService.login(chatId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Track> tracks = (List<Track>) linkService.listByChatId(user.getChatId());
        if (tracks.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        return ResponseEntity.ok(tracks.stream().map(Track::getUrl));
    }
}
