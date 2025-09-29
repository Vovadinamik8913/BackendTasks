package edu.java.scrapper.dto.jdbc;

import lombok.Getter;

@Getter
public  class JdbcUser {
    private final Long id;
    private final Long chatId;

    public JdbcUser(long id, long chatId) {
        this.id = id;
        this.chatId = chatId;
    }

    public JdbcUser(long chatId) {
        this.id = 0L;
        this.chatId = chatId;
    }
}
