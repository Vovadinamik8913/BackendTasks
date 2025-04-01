package edu.java.scrapper.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<Long> chats;
    public UserService() {
        chats = new ArrayList<>();
    }

    public void addUser(Long chatId) {
        chats.add(chatId);
    }

    public boolean isUserRegistered(Long chatId) {
        return chats.contains(chatId);
    }
}
