package edu.java.bot.messages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Setter
public class HelpCommand implements Command {
    private List<Command> commands;

    public HelpCommand() {
        this.commands = new ArrayList<>();
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "list of available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        StringBuilder description = new StringBuilder();
        for (Command command : commands) {
            description.append(command.command());
            description.append(" : ");
            description.append(command.description());
            description.append("\n");
        }
        return new SendMessage(chatId, description.toString());
    }
}
