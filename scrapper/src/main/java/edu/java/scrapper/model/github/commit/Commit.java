package edu.java.scrapper.model.github.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Commit {
    private Worker author;
    private Worker committer;
    private String message;
    @JsonProperty("comment_count")
    private Long commentCount;
    private Verification verification;
}
