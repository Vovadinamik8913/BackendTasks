package edu.java.scrapper.configuration;

import edu.java.scrapper.domain.JdbcLinkRepository;
import edu.java.scrapper.domain.JdbcUserRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.UserService;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import edu.java.scrapper.service.jdbc.JdbcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DbConfig {
    @Bean
    public LinkService getLinkService(
        JdbcTemplate jdbcTemplate,
        JdbcLinkRepository linkRepository
    ) {
        return new JdbcLinkService(jdbcTemplate, linkRepository);
    }

    @Bean
    public UserService getUserService(
        JdbcTemplate jdbcTemplate,
        JdbcUserRepository userRepository,
        JdbcLinkRepository linkRepository
    ) {
        return new JdbcUserService(jdbcTemplate, userRepository, linkRepository);
    }
}
