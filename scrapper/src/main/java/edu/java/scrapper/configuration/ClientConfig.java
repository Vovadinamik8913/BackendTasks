package edu.java.scrapper.configuration;

import edu.java.scrapper.model.github.GitClient;
import edu.java.scrapper.service.BotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {
    @Value("${app.bot-url}")
    private String botUrl;

    @Bean
    public GitClient gitClient() {
        WebClient webClient = WebClient.builder()
            .baseUrl("https://api.github.com/")
            .codecs(clientCodecConfigurer -> {
                clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder());
                clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder());
            })
            .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient)).build();
        return factory.createClient(GitClient.class);
    }

    @Bean
    public BotService gitBot() {
        RestClient restClient = RestClient.builder()
            .baseUrl(botUrl)
            .messageConverters(converters -> {
                converters.add(new MappingJackson2HttpMessageConverter());
                converters.add(new StringHttpMessageConverter());
            })
            .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build();
        return factory.createClient(BotService.class);
    }
}
