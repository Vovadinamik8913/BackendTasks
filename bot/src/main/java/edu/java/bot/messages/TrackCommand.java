package edu.java.bot.messages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.ChatState;
import edu.java.bot.service.ChatStateHolder;
import edu.java.bot.service.ScrapperService;
import org.springframework.stereotype.Component;

public class TrackCommand implements Command {
    private final ChatStateHolder stateHolder;
    private final ScrapperService scrapperService;

    public TrackCommand(ChatStateHolder stateHolder, ScrapperService scrapperService) {
        this.stateHolder = stateHolder;
        this.scrapperService = scrapperService;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "track a new URL";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        if (!stateHolder.hasState(chatId)) {
            stateHolder.setState(chatId, ChatState.TRACK);
            return new SendMessage(chatId, "Please send the URL you want to track");
        }

        if (stateHolder.getState(chatId) == ChatState.TRACK) {
            String url = update.message().text();
            stateHolder.clearState(chatId);

            try {
                String response = scrapperService.addLink(chatId, url);
                return new SendMessage(chatId, response);
            } catch (Exception e) {
                return new SendMessage(chatId, "Failed to add URL: " + e.getMessage());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Update update) {
        if (update.message() == null || update.message().text() == null) {
            return false;
        }

        long chatId = update.message().chat().id();
        return command().equals(update.message().text())
            || stateHolder.getState(chatId) == ChatState.TRACK;
    }
}
