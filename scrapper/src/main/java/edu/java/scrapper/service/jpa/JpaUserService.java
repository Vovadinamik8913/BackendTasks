package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.domain.jpa.JpaUserRepository;
import edu.java.scrapper.dto.User;
import edu.java.scrapper.dto.jpa.JpaUser;
import edu.java.scrapper.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaUserService implements UserService {
    private final JpaUserRepository userRepository;
    private final JpaLinkRepository linkRepository;

    @Override
    public void register(long tgChatId) {
        userRepository.save(new JpaUser(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        linkRepository.deleteAllByUser_ChatId(tgChatId);
        userRepository.deleteByChatId(tgChatId);
    }

    @Override
    public User login(long tgChatId) {
        JpaUser userDto = userRepository.findByChatId(tgChatId).orElse(null);
        if (userDto == null) {
            return null;
        }
        return new User(userDto.getId(), userDto.getChatId());
    }
}
