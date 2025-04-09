package edu.java.scrapper.model.github.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Worker {
    private String name;
    private String email;
    private OffsetDateTime date;
}
