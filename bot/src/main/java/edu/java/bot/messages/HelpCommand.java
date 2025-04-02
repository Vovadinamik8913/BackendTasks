package edu.java.bot.messages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;

public class HelpCommand implements Command {
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
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
