package edu.java.scrapper.domain;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.configuration.JdbcConfig;
import edu.java.scrapper.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(classes = {
        JdbcConfig.class,
        JdbcUserRepository.class
})
class UserRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcUserRepository jdbcUserRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        UserDto user1 = jdbcUserRepository.add(123);
        UserDto user2 = jdbcUserRepository.add(321);
        Assertions.assertTrue(user2.getId() != 0L);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        UserDto user1 = jdbcUserRepository.add(123);
        Assertions.assertTrue(jdbcUserRepository.remove(123L));
    }
}
