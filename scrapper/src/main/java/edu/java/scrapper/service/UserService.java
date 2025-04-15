package edu.java.scrapper.service;

import edu.java.scrapper.dto.User;
import jakarta.annotation.Nullable;

public interface UserService {
    void register(long tgChatId);
    void unregister(long tgChatId);
    @Nullable User login(long tgChatId);
}
