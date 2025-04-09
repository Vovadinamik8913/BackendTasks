package edu.java.scrapper.controller;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.LinkType;
import edu.java.scrapper.service.TrackService;
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
import edu.java.scrapper.model.Parser;

@RestController
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;
    private final UserService userService;

    @PostMapping("/track")
    public ResponseEntity<?> addTrack(
        @RequestParam("chatId") Long chatId,
        @RequestParam("service") String serviceUrl
    ) {
        if (!userService.isUserRegistered(chatId)) {
            return ResponseEntity.notFound().build();
        }
        LinkType type = LinkType.getByUrl(serviceUrl);
        if (type == null) {
            return ResponseEntity.notFound().build();
        }
        Link link = type.getParser().parse(serviceUrl);
        trackService.addTrack(chatId, link);
        return ResponseEntity.ok("Сервис " + serviceUrl + " добавлен в отслеживание");
    }

    @DeleteMapping("/untrack")
    public ResponseEntity<?> removeTrack(
        @RequestParam("chatId") Long chatId,
        @RequestParam("service") Long serviceIndex
    ) {
        if (!userService.isUserRegistered(chatId)) {
            return ResponseEntity.notFound().build();
        }
        Link link = trackService.removeTrack(chatId, serviceIndex - 1);
        if (link == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Сервис " + link.getHref() + " больше не отслеживается");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTracks(
        @RequestParam("chatId") Long chatId
    ) {
        if (!userService.isUserRegistered(chatId)) {
            return ResponseEntity.notFound().build();
        }
        List<Link> tracks = trackService.getTracks(chatId);
        if (tracks.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        return ResponseEntity.ok(tracks);
    }
}
