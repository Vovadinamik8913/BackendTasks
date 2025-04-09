package edu.java.scrapper.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class User {
    public String login;
    private String html_url;
}
