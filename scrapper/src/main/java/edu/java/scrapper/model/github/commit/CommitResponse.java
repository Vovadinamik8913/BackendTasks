package edu.java.scrapper.model.github.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CommitResponse {
    private String sha;
    private Commit commit;
    @JsonProperty("html_url")
    private String htmlUrl;
}
