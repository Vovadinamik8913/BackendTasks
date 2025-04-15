package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.dto.jdbc.JdbcLink;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcLinkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<JdbcLink> linkRowMapper = (rs, rowNum) ->
        new JdbcLink(
            rs.getLong("id"),
            rs.getString("url"),
            rs.getTimestamp("updated_at"),
            rs.getLong("chat_id")
        );

    private static final int DISTANCE = 60;

    public List<JdbcLink> findAll() {
        String query = "select * from links where extract(epoch from (now() - updated_at)) >= ?";
        return jdbcTemplate.query(query, linkRowMapper, DISTANCE);
    }

    public List<JdbcLink> findAll(long chatId) {
        String query = "select * from links where chat_id = ?";
        return jdbcTemplate.query(query, linkRowMapper, chatId);
    }

    @Transactional
    public JdbcLink add(long chatId, String url, Timestamp updateAt) {
        String sql = "insert into links (url, updated_at, chat_id) values (?,?,?) returning *;";
        return jdbcTemplate.queryForObject(sql, linkRowMapper, url, updateAt, chatId);
    }

    @Transactional
    public void updateTime(long chatId, String url, Timestamp updateAt) {
        String sql = "update links set updated_at = ? where chat_id = ? and url = ?";
        jdbcTemplate.update(sql, updateAt, chatId, url);
    }


    @Transactional
    public boolean remove(Long chatId, String url) {
        String sql = "delete from links where chat_id = ? and url = ?;";
        int row = jdbcTemplate.update(sql, chatId, url);
        return row > 0;
    }

    @Transactional
    public boolean removeAll(Long chatId) {
        String sql = "delete from links where chat_id = ?;";
        int row = jdbcTemplate.update(sql, chatId);
        return row > 0;
    }
}
