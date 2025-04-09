package edu.java.scrapper.model.github.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Verification {
    private boolean verified;
    private String reason;
    @JsonProperty("verified_at")
    private OffsetDateTime verifiedAt;
}
