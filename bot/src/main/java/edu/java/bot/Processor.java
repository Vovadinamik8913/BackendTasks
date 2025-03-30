package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.messages.Command;
import java.util.List;

public interface Processor {
    List<? extends Command> commands();

    SendMessage process(Update update);
}
