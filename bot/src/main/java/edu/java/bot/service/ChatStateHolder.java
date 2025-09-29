package edu.java.bot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ChatStateHolder {
    private final Map<Long, ChatState> chatStates = new ConcurrentHashMap<>();

    public void setState(long chatId, ChatState state) {
        chatStates.put(chatId, state);
    }

    public ChatState getState(long chatId) {
        return chatStates.get(chatId);
    }

    public boolean hasState(long chatId) {
        return chatStates.containsKey(chatId);
    }

    public void clearState(long chatId) {
        chatStates.remove(chatId);
    }
}
