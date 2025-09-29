package edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${app.scheduler.enable}")
    private boolean schedulerEnable;

    @Value("${app.scheduler.force-check-delay}")
    private Duration schedulerForceCheckDelay;

    @Value("${app.scheduler.interval}")
    private Duration interval;

    @Bean
    public Scheduler scheduler() {
        return new Scheduler(schedulerEnable, schedulerForceCheckDelay, interval);
    }

    public record Scheduler(
        boolean enable,
        @NotNull Duration interval,
        @NotNull Duration forceCheckDelay
    ) {}
}
