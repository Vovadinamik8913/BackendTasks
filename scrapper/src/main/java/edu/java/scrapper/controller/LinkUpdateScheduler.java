package edu.java.scrapper.controller;

import edu.java.shared.LinkUpdate;
import edu.java.scrapper.model.LinkType;
import edu.java.scrapper.model.github.GitClient;
import edu.java.scrapper.model.github.GitLink;
import edu.java.scrapper.model.github.repository.Repository;
import edu.java.scrapper.service.BotService;
import edu.java.scrapper.service.TrackService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class LinkUpdateScheduler {
    private final GitClient client;
    private final BotService bot;
    private final TrackService trackService;
    private static final Logger log = LoggerFactory.getLogger(LinkUpdateScheduler.class);


    @Scheduled(fixedDelayString = "#{@applicationConfig.scheduler().interval().toMillis()}",
               initialDelayString = "#{@applicationConfig.scheduler().forceCheckDelay().toMillis()}")
    public void update() {
        for (var link : trackService.getTracks()) {
            for (var trace : link.getValue()) {
                if (trace.getType() == LinkType.GITHUB) {
                    GitLink gitLink = (GitLink) trace;
                    Repository repository = client.getRepository(gitLink.getOwner(), gitLink.getRepository());
                    if (repository.getUpdatedAt().isAfter(trace.getViewedAt())) {
                        ArrayList<Long> id = new ArrayList<>();
                        id.add(link.getKey());
                        trace.setViewedAt(repository.getUpdatedAt());
                        bot.update(new LinkUpdate(0L, repository.getHtmlUrl(),
                            "changed at" + repository.getUpdatedAt(), id));
                    }
                }
            }
        }
    }
}
