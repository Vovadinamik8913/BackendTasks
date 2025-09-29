package edu.java.scrapper.domain.jpa;

import edu.java.scrapper.dto.jpa.JpaLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JpaLinkRepository extends JpaRepository<JpaLink, Long> {
    List<JpaLink> findAllByUser_ChatId(Long chatId);

    void deleteByUser_ChatIdAndUrl(Long chatId, String url);
    void deleteAllByUser_ChatId(Long chatId);

    @Modifying
    @Query(value = """
        UPDATE links l SET l.updatedAt = :#{#link.updatedAt}
        WHERE l.user.chatId = :chatId AND l.url = :url
        """,
           nativeQuery = true)
    void updateLink(@Param("chatId") Long chatId,
        @Param("url") String url,
        @Param("link") JpaLink link);

    // Fixed native query
    @Query(value = """
        SELECT * FROM links
        WHERE EXTRACT(EPOCH FROM (NOW() - updated_at)) >= :distance
        """, nativeQuery = true)
    List<JpaLink> findLinksOlderThan(@Param("distance") long distanceInSeconds);
}
