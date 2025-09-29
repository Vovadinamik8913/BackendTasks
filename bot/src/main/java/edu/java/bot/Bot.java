package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.messages.Command;
import edu.java.bot.messages.UpdateCommand;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Bot implements AutoCloseable, UpdatesListener {
    private final TelegramBot bot;
    private final Processor processor;

    public Bot(
        ApplicationConfig properties,
        Processor processor
    ) {
        bot = new TelegramBot(properties.telegramToken());
        this.processor = processor;
        setupCommandsMenu();
        start();
    }

    private void setupCommandsMenu() {
        List<BotCommand> commands = processor.commands().stream()
            .filter(command -> !(command instanceof UpdateCommand))
            .map(Command::toApiCommand)
            .toList();
        bot.execute(new SetMyCommands(commands.toArray(new BotCommand[0])));
    }

    public  <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            SendMessage response = processor.process(update);
            if (response != null) {
                bot.execute(response);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void start() {
        bot.setUpdatesListener(this);
    }

    @Override
    public  void close() {
        bot.shutdown();
    }
}
