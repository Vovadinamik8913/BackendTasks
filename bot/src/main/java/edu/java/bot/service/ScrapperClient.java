package edu.java.bot.service;

import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class ScrapperClient {
    private final RestTemplate restTemplate;
    private final String scrapperBaseUrl;

    public ScrapperClient(
        RestTemplate restTemplate,
        ApplicationConfig applicationConfig
    ) {
        this.restTemplate = restTemplate;
        this.scrapperBaseUrl = applicationConfig.scrapperBaseUrl();
    }

    public String addLink(long chatId, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("chatId", String.valueOf(chatId));
        params.add("service", url);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            scrapperBaseUrl + "/track",
            request,
            String.class
        );

        return response.getBody();
    }

    public String removeLink(long chatId, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("chatId", String.valueOf(chatId));
        params.add("service", url);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            scrapperBaseUrl + "/untrack?chatId=" + chatId + "&service=" + url,
            HttpMethod.DELETE,
            request,
            String.class
        );

        return response.getBody();
    }

    public String start(long chatId) {
        ResponseEntity<String> response = restTemplate.getForEntity(
            scrapperBaseUrl + "/start?chatId=" + chatId,
            String.class
        );

        return response.getBody();
    }

    public String tracksList(long chatId) {
        ResponseEntity<?> response = restTemplate.getForEntity(
            scrapperBaseUrl + "/list?chatId=" + chatId,
            Object.class
        );

        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
           return "пока что ничего не отслеживается";
        }
        List<String> tracks = (List<String>) response.getBody();
        StringBuilder res = new StringBuilder();
        for (String track : tracks) {
            res.append(track).append("\n");
        }
        return res.toString();
    }
}
