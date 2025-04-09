package edu.java.scrapper.model.github.pulls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.model.github.User;
import lombok.Getter;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Pull {
    private Long number;
    private String state;
    private String title;
    private User user;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
