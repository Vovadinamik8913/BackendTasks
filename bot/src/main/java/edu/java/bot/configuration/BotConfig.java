package edu.java.bot.configuration;

import edu.java.bot.Processor;
import edu.java.bot.UserMessageProcessor;
import edu.java.bot.messages.Command;
import edu.java.bot.messages.HelpCommand;
import edu.java.bot.messages.ListCommand;
import edu.java.bot.messages.StartCommand;
import edu.java.bot.messages.TrackCommand;
import edu.java.bot.messages.UntrackCommand;
import edu.java.bot.messages.UpdateCommand;
import edu.java.bot.service.ChatStateHolder;
import edu.java.bot.service.ScrapperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BotConfig {
    private final List<Command> commands;

    public BotConfig(ChatStateHolder stateHolder, ScrapperService scrapperService) {
        commands = new ArrayList<>();
        commands.add(new TrackCommand(stateHolder, scrapperService));
        commands.add(new UpdateCommand(stateHolder));
        commands.add(new UntrackCommand(stateHolder, scrapperService));
        commands.add(new ListCommand(scrapperService));
        commands.add(new StartCommand(scrapperService));
        commands.add(new HelpCommand(commands));
    }
    @Bean
    public Processor userMessageProcessor() {
        return new UserMessageProcessor(commands);
    }
}
