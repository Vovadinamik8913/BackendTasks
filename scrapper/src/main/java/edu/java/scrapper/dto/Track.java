package edu.java.scrapper.dto;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.LinkType;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Track {
    private String url;
    private long chatId;

    @Nullable
    public Link toLink() {
        LinkType type = LinkType.getByUrl(url);
        if (type == null) {
            return null;
        }
        return type.getParser().parse(url);
    }
}
