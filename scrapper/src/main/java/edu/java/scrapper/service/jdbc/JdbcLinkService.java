package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import edu.java.scrapper.dto.Track;
import edu.java.scrapper.dto.jdbc.JdbcLink;
import edu.java.scrapper.service.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    public Track add(long chatId, String url) {
        JdbcLink linkDto = jdbcLinkRepository.add(
            chatId,
            url,
            Timestamp.from(OffsetDateTime.now().toInstant()));
        return new Track(linkDto.getUrl(), linkDto.getChatId());
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        return jdbcLinkRepository.remove(tgChatId,  url);
    }

    @Override
    public Collection<Track> listAll() {
        List<JdbcLink> list = jdbcLinkRepository.findAll();
        return list.stream().map(l -> new Track(l.getUrl(), l.getChatId())).toList();
    }

    @Override
    public Collection<Track> listByChatId(long tgChatId) {
        List<JdbcLink> list = jdbcLinkRepository.findAll(tgChatId);
        return list.stream().map(l -> new Track(l.getUrl(), l.getChatId())).toList();
    }

    @Override
    public void update(long tgChatId, String url) {
        jdbcLinkRepository.updateTime(tgChatId, url, Timestamp.from(OffsetDateTime.now().toInstant()));
    }
}
