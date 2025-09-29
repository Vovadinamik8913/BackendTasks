package edu.java.scrapper.model.github.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Repository {
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String description;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
    @JsonProperty("pushed_at")
    private OffsetDateTime pushedAt;
}
