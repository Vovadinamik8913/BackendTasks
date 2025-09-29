package edu.java.scrapper.model.github;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.LinkType;
import edu.java.scrapper.model.Parser;
import org.jetbrains.annotations.NotNull;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitParser implements Parser {
    private static final Pattern GITHUB_PATTERN = Pattern.compile(
        "^https?://github.com/(?<owner>[^/]+)/(?<repo>[^/]+)" +
            "(/?$|/tree/(?<branch>[^/]+)" +
            "|/pull/(?<pr>\\d+)" +
            "|/pulls?/?$)" +
            "(.*)?"
    );

    @NotNull
    private static GitLink getGitLink(String url, Matcher matcher, URI uri) {
        GitLink gitLink = new GitLink(
            url,
            LinkType.GITHUB
        );

        gitLink.setOwner(matcher.group("owner"));
        gitLink.setRepository(matcher.group("repo"));

        String branch = matcher.group("branch");
        if (branch != null) {
            gitLink.setBranch(branch);
        }

        String pr = matcher.group("pr");
        if (pr != null) {
            gitLink.setPullRequest(Long.parseLong(pr));
            gitLink.setPulls(false);
        }

        if (uri.getPath().contains("/pulls")) {
            gitLink.setPulls(true);
        }
        return gitLink;
    }

    @Override
    public Link parse(String url) {
        try {
            URI uri = URI.create(url.trim());
            if (!"github.com".equals(uri.getHost())) {
                throw new IllegalArgumentException("Not a GitHub URL");
            }
            System.out.println(url);
            Matcher matcher = GITHUB_PATTERN.matcher(url);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid GitHub URL format");
            }

            return getGitLink(url, matcher, uri);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid GitHub URL: " + e.getMessage());
        }
    }
}
