package edu.java.scrapper.service;

import edu.java.scrapper.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void register(long tgChatId);
    void unregister(long tgChatId);
    UserDto login(long tgChatId);
}
