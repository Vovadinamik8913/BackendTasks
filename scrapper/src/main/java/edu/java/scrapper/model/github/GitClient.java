package edu.java.scrapper.model.github;

import edu.java.scrapper.model.github.commit.Commit;
import edu.java.scrapper.model.github.commit.CommitResponse;
import edu.java.scrapper.model.github.pulls.Pull;
import edu.java.scrapper.model.github.repository.Activity;
import edu.java.scrapper.model.github.repository.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import java.util.List;

@HttpExchange(url = "/repos/{owner}/{repo}", accept = "application/vnd.github.v3+json")
public interface GitClient {
    @GetExchange
    Repository getRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/activity")
    List<Activity> getActivities(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/commits?state=all&per_page=100")
    List<CommitResponse> getRepositoryCommits(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/pulls?state=all&per_page=100")
    List<Pull> getPulls(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/pulls/{number}")
    Pull getPull(@PathVariable String owner, @PathVariable String repo, @PathVariable Long number);

    @GetExchange("/pulls/{number}/commits?state=all&per_page=100")
    List<Commit> getPullCommits(@PathVariable String owner, @PathVariable String repo, @PathVariable Long number);
}
