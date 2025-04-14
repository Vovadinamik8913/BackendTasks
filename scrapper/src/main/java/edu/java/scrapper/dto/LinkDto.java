package edu.java.scrapper.dto;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.LinkType;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class LinkDto {
    private final long id;
    private final String url;
    private final java.sql.Timestamp updatedAt;
    private final long chatId;

    public LinkDto(long id, String url, java.sql.Timestamp updatedAt, long chatId) {
        this.id = id;
        this.url = url;
        this.updatedAt = updatedAt;
        this.chatId = chatId;
    }

    public LinkDto(String url, java.sql.Timestamp updatedAt, long chatId) {
        this.id = 0L;
        this.url = url;
        this.updatedAt = updatedAt;
        this.chatId = chatId;
    }

    @Nullable
    public Link toLink() {
        LinkType type = LinkType.getByUrl(url);
        if (type == null) {
            return null;
        }
        return type.getParser().parse(url);
    }
}
