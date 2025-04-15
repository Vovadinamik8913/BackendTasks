package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.domain.jpa.JpaUserRepository;
import edu.java.scrapper.dto.Track;
import edu.java.scrapper.dto.jpa.JpaLink;
import edu.java.scrapper.dto.jpa.JpaUser;
import edu.java.scrapper.service.LinkService;
import lombok.RequiredArgsConstructor;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaUserRepository jpaUserRepository;
    private static int DISTANCE = 60;

    @Override
    public Track add(long tgChatId, String url) {
        JpaUser userDto = jpaUserRepository.findByChatId(tgChatId).orElse(null);
        if (userDto == null) {
            return null;
        }
        JpaLink jpaLink = jpaLinkRepository.save(new JpaLink(
            url, userDto
        ));
        return new Track(jpaLink.getUrl(), jpaLink.getUser().getId());
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        jpaLinkRepository.deleteByUser_ChatIdAndUrl(tgChatId, url);
        return true;
    }

    @Override
    public Collection<Track> listAll() {
        List<JpaLink> list = jpaLinkRepository.findLinksOlderThan(DISTANCE);
        return list.stream().map(l -> new Track(l.getUrl(), l.getUser().getId())).toList();
    }

    @Override
    public Collection<Track> listByChatId(long tgChatId) {
        List<JpaLink> list = jpaLinkRepository.findAllByUser_ChatId(tgChatId);
        return list.stream().map(l -> new Track(l.getUrl(), l.getUser().getId())).toList();
    }

    @Override
    public void update(long tgChatId, String url) {
        JpaUser userDto = jpaUserRepository.findByChatId(tgChatId).orElse(null);
        if (userDto == null) {
            return;
        }
        jpaLinkRepository.updateLink(
            tgChatId,
            url,
            new JpaLink(url, userDto)
            );
    }
}
