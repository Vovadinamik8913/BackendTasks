package edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.components.LinkUpdate;
import edu.java.bot.service.ChatState;
import edu.java.bot.service.ChatStateHolder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UpdateController {
    private final Bot bot;
    private final ChatStateHolder chatState;

    @PostMapping("/update")
    @Operation(summary = "Отправить обновление")
    public @ResponseBody ResponseEntity<String> update(
        @RequestBody LinkUpdate linkUpdate
    ) {
        System.out.println(linkUpdate.getUrl());
        for (Long id : linkUpdate.getChatIds()) {
            String info = linkUpdate.getUrl() + " - " + linkUpdate.getDescription();
            chatState.setState(id, ChatState.UPDATE);
            bot.execute(new SendMessage(id, info));
        }
        return ResponseEntity.ok("Обновление обработано");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleException(Exception exception) {
        return new ErrorMessage("Некорректные параметры запроса");
    }
}
