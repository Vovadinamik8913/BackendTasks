package edu.java.scrapper.configuration;

import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.domain.jpa.JpaUserRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.UserService;
import edu.java.scrapper.service.jpa.JpaLinkService;
import edu.java.scrapper.service.jpa.JpaUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    public LinkService getLinkService(
        JpaLinkRepository jpaLinkRepository,
        JpaUserRepository jpaUserRepository
    ) {
        return new JpaLinkService(jpaLinkRepository, jpaUserRepository);
    }

    @Bean
    public UserService getUserService(
        JpaLinkRepository jpaLinkRepository,
        JpaUserRepository jpaUserRepository
    ) {
        return new JpaUserService(jpaUserRepository, jpaLinkRepository);
    }
}
