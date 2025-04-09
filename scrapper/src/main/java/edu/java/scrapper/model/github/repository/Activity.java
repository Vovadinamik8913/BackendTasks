package edu.java.scrapper.model.github.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Activity {
    private String before;
    private String after;
    private OffsetDateTime timestamp;
    @JsonProperty("activity_type")
    private String activityType;
    private String ref;
}
