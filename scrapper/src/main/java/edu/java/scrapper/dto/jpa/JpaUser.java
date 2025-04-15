package edu.java.scrapper.dto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@Getter
@NoArgsConstructor
public class JpaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<JpaLink> links = new ArrayList<>();

    public JpaUser(long chatId) {
        this.chatId = chatId;
    }
}
