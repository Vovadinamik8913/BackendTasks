package edu.java.scrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private long id;
    private long chatId;

    public User(long chatId) {
        this.chatId = chatId;
    }
}
