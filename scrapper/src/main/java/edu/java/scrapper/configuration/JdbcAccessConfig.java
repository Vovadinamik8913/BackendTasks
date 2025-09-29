package edu.java.scrapper.configuration;

import edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import edu.java.scrapper.domain.jdbc.JdbcUserRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.UserService;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import edu.java.scrapper.service.jdbc.JdbcUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

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
