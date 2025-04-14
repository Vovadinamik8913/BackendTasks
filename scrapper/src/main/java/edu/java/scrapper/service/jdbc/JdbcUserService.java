package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.JdbcLinkRepository;
import edu.java.scrapper.domain.JdbcUserRepository;
import edu.java.scrapper.dto.UserDto;
import edu.java.scrapper.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public class JdbcUserService implements UserService {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserRepository jdbcUserRepository;
    private final JdbcLinkRepository linkRepository;

    @Override
    public void register(long tgChatId) {
        jdbcUserRepository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        linkRepository.removeAll(tgChatId);
        jdbcUserRepository.remove(tgChatId);
    }

    @Override
    public UserDto login(long tgChatId) {
        return jdbcUserRepository.findById(tgChatId);
    }
}
