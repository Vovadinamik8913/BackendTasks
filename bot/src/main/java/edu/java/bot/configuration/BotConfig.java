package edu.java.bot.configuration;

import edu.java.bot.Processor;
import edu.java.bot.UserMessageProcessor;
import edu.java.bot.messages.Command;
import edu.java.bot.messages.HelpCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BotConfig {
    List<Command> commands;

    public BotConfig() {
        commands = new ArrayList<>();
        HelpCommand helpCommand = new HelpCommand();
        commands.add(helpCommand);
        helpCommand.setCommands(commands);
    }
    @Bean
    public Processor userMessageProcessor() {
        return new UserMessageProcessor(commands);
    }
}
