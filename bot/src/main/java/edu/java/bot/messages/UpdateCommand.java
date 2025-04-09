package edu.java.bot.messages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.ChatState;
import edu.java.bot.service.ChatStateHolder;
import edu.java.bot.service.ScrapperService;

public class UpdateCommand implements Command {
    private final ChatStateHolder stateHolder;

    public UpdateCommand(ChatStateHolder stateHolder) {
        this.stateHolder = stateHolder;
    }

    @Override
    public String command() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        if (!stateHolder.hasState(chatId)) {
            stateHolder.setState(chatId, ChatState.UPDATE);
            return null;
        }

        if (stateHolder.getState(chatId) == ChatState.UPDATE) {
            String url = update.message().text();
            stateHolder.clearState(chatId);

            try {
                return new SendMessage(chatId, url);
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
        return stateHolder.getState(chatId) == ChatState.UPDATE;
    }
}
