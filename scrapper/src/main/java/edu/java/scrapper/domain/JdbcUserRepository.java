package edu.java.scrapper.domain;

import edu.java.scrapper.dto.UserDto;
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
    private final RowMapper<UserDto> userRowMapper = (rs, rowNum) ->
        new UserDto(
          rs.getLong("id"),
          rs.getLong("chat_id")
        );

    public List<UserDto> findAll() {
        return jdbcTemplate.query("select * from chats;", userRowMapper);
    }

    @Nullable
    public UserDto findById(Long chatId) {
        return jdbcTemplate.queryForObject(
            "select * from chats where chat_id = ?", userRowMapper, chatId
        );
    }

    @Transactional
    public UserDto add(long chatId) {
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
