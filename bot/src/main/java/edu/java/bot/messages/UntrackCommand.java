package edu.java.bot.messages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.ChatState;
import edu.java.bot.service.ChatStateHolder;
import edu.java.bot.service.ScrapperService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class UntrackCommand implements Command{
    private final ChatStateHolder stateHolder;
    private final ScrapperService scrapperService;

    public UntrackCommand(ChatStateHolder stateHolder, ScrapperService scrapperService) {
        this.stateHolder = stateHolder;
        this.scrapperService = scrapperService;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "untrack url";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        if (!stateHolder.hasState(chatId)) {
            stateHolder.setState(chatId, ChatState.UNTRACK);
            return new SendMessage(chatId, "Please send the URL you want to untrack");
        }

        if (stateHolder.getState(chatId) == ChatState.UNTRACK) {
            String url = update.message().text();
            stateHolder.clearState(chatId);

            try {
                String response = scrapperService.removeLink(chatId, url);
                return new SendMessage(chatId, response);
            } catch (Exception e) {
                return new SendMessage(chatId, "Failed to remove URL: " + e.getMessage());
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
            || stateHolder.getState(chatId) == ChatState.UNTRACK;
    }
}
