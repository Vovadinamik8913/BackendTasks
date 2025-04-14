package edu.java.scrapper.controller;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.service.LinkService;
import edu.java.shared.LinkUpdate;
import edu.java.scrapper.model.LinkType;
import edu.java.scrapper.model.github.GitClient;
import edu.java.scrapper.model.github.GitLink;
import edu.java.scrapper.model.github.repository.Repository;
import edu.java.scrapper.service.BotService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class LinkUpdateScheduler {
    private final GitClient client;
    private final BotService bot;
    private final LinkService linkService;


    @Scheduled(fixedDelayString = "#{@applicationConfig.scheduler().interval().toMillis()}",
               initialDelayString = "#{@applicationConfig.scheduler().forceCheckDelay().toMillis()}")
    public void update() {
        for (var link : linkService.listAll()) {
            Link trace = link.toLink();
            if (trace == null) {
                continue;
            }
            if (trace.getType() == LinkType.GITHUB) {
                GitLink gitLink = (GitLink) trace;
                Repository repository = client.getRepository(gitLink.getOwner(), gitLink.getRepository());
                if (repository.getUpdatedAt().isBefore(trace.getViewedAt())) {
                    ArrayList<Long> id = new ArrayList<>();
                    id.add(link.getChatId());
                    trace.setViewedAt(repository.getUpdatedAt());
                    bot.update(new LinkUpdate(0L, repository.getHtmlUrl(),
                        "changed at" + repository.getUpdatedAt(), id));
                    linkService.update(link.getChatId(), link.getUrl());
                }
            }
        }
    }
}
