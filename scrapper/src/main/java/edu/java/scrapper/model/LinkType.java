package edu.java.scrapper.model;

import edu.java.scrapper.model.github.GitParser;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public enum LinkType {
    GITHUB("https://github.com/", new GitParser()),
    STACKOVERFLOW("https://stackoverflow.com/", null),
    ;

    private final String url;
    private final Parser parser;
    LinkType(String url, Parser parser) {
        this.url = url;
        this.parser = parser;
    }

    @Nullable
    public static LinkType getByUrl(String url) {
        for (LinkType linkType : LinkType.values()) {
            if (url.startsWith(linkType.url)) {
                return linkType;
            }
        }
        return null;
    }
}
