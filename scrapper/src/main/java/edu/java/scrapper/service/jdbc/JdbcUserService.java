package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import edu.java.scrapper.domain.jdbc.JdbcUserRepository;
import edu.java.scrapper.dto.User;
import edu.java.scrapper.dto.jdbc.JdbcUser;
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
    public User login(long tgChatId) {
        JdbcUser userDto = jdbcUserRepository.findById(tgChatId);
        if (userDto == null) {
            return null;
        }
        return new User(userDto.getId(), userDto.getChatId());
    }
}
