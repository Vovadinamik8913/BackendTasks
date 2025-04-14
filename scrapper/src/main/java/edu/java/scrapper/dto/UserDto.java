package edu.java.scrapper.dto;

import lombok.Getter;

@Getter
public  class UserDto {
    private final Long id;
    private final Long chatId;

    public UserDto(long id, long chatId) {
        this.id = id;
        this.chatId = chatId;
    }

    public UserDto(long chatId) {
        this.id = 0L;
        this.chatId = chatId;
    }
}
