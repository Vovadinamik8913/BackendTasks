package edu.java.bot.messages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.ScrapperService;

public class ListCommand implements Command {
    private final ScrapperService scrapperService;

    public ListCommand(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "list of trackable sites";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        try {
            String result = scrapperService.tracksList(chatId);
            return new SendMessage(chatId, result);
        } catch (Exception e) {
            return new SendMessage(chatId, e.getMessage());
        }
    }
}
