package edu.java.bot.components;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema
public class LinkUpdate {
    private Long id;
    private String url;
    private String description;
    private List<Long> chatIds;
}
