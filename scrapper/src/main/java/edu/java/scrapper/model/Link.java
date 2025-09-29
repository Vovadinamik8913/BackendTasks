package edu.java.scrapper.model;

import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
public abstract class Link {
    private final String href;
    private final LinkType type;
    @Setter
    private OffsetDateTime viewedAt;

    public Link(String href, LinkType type) {
        this.href = href;
        this.type = type;
        viewedAt = OffsetDateTime.now();
    }
}
