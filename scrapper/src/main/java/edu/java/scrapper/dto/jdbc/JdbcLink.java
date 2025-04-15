package edu.java.scrapper.dto.jdbc;

import lombok.Getter;

@Getter
public class JdbcLink {
    private final long id;
    private final String url;
    private final java.sql.Timestamp updatedAt;
    private final long chatId;

    public JdbcLink(long id, String url, java.sql.Timestamp updatedAt, long chatId) {
        this.id = id;
        this.url = url;
        this.updatedAt = updatedAt;
        this.chatId = chatId;
    }

    public JdbcLink(String url, java.sql.Timestamp updatedAt, long chatId) {
        this.id = 0L;
        this.url = url;
        this.updatedAt = updatedAt;
        this.chatId = chatId;
    }
}
