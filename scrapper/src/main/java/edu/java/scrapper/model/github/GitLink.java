package edu.java.scrapper.model.github;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.LinkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitLink extends Link {
    private String repository;
    private String branch;
    private String owner;
    private Long pullRequest;
    private boolean pulls;

    public GitLink(String href, LinkType type) {
        super(href, type);
    }
}
