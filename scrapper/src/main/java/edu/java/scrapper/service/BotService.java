package edu.java.scrapper.service;

import edu.java.bot.components.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface BotService {
    @PostExchange("/update")
    ResponseEntity<String> update(@RequestBody LinkUpdate linkUpdate);
}
