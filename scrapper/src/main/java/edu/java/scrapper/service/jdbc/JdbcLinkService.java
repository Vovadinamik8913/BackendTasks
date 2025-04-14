package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.JdbcLinkRepository;
import edu.java.scrapper.dto.LinkDto;
import edu.java.scrapper.service.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Collection;

@AllArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    public LinkDto add(long userId, String url) {
        return jdbcLinkRepository.add(
            userId,
            url,
            Timestamp.from(OffsetDateTime.now().toInstant()));
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        return jdbcLinkRepository.remove(tgChatId,  url);
    }

    @Override
    public Collection<LinkDto> listAll() {
        return jdbcLinkRepository.findAll();
    }

    @Override
    public Collection<LinkDto> listByChatId(long tgChatId) {
        return jdbcLinkRepository.findAll(tgChatId);
    }

    @Override
    public void update(long tgChatId, String url) {
        jdbcLinkRepository.updateTime(tgChatId, url, Timestamp.from(OffsetDateTime.now().toInstant()));
    }
}
