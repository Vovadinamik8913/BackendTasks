package edu.java.scrapper.domain.jpa;

import edu.java.scrapper.dto.jpa.JpaUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {
    Optional<JpaUser> findByChatId(Long chatId);
    void deleteByChatId(Long chatId);
    @NotNull List<JpaUser> findAll();
}
