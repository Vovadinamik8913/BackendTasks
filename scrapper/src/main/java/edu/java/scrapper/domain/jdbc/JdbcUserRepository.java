package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.dto.jdbc.JdbcUser;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcUserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<JdbcUser> userRowMapper = (rs, rowNum) ->
        new JdbcUser(
          rs.getLong("id"),
          rs.getLong("chat_id")
        );

    public List<JdbcUser> findAll() {
        return jdbcTemplate.query("select * from chats;", userRowMapper);
    }

    @Nullable
    public JdbcUser findById(Long chatId) {
        return jdbcTemplate.queryForObject(
            "select * from chats where chat_id = ?", userRowMapper, chatId
        );
    }

    @Transactional
    public JdbcUser add(long chatId) {
        String sql = "insert into chats (chat_id) values (?) returning *;";
        return jdbcTemplate.queryForObject(sql, userRowMapper, chatId);
    }

    @Transactional
    public boolean remove(Long chatId) {
        String sql = "delete from chats where chat_id = ?;";
        int row = jdbcTemplate.update(sql, chatId);
        return row > 0;
    }


}
