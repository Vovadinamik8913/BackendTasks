package edu.java.bot.service;

import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ScrapperService {
    private final RestTemplate restTemplate;
    private final String scrapperBaseUrl;

    public ScrapperService(
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
        params.add("service", url);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            scrapperBaseUrl + "/track",
            request,
            String.class
        );

        return response.getBody();
    }

    public String removeLink(long chatId, Long index) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Long> params = new LinkedMultiValueMap<>();
        params.add("service", index);
        HttpEntity<MultiValueMap<String, Long>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            scrapperBaseUrl + "/untrack",
            HttpMethod.DELETE,
            request,
            String.class
        );

        return response.getBody();
    }

    public String start(long chatId) {
        ResponseEntity<String> response = restTemplate.getForEntity(
            scrapperBaseUrl + "/start",
            String.class
        );

        return response.getBody();
    }

    public String tracksList(long chatId) {
        ResponseEntity<String> response = restTemplate.getForEntity(
            scrapperBaseUrl + "/list",
            String.class
        );

        return response.getBody();
    }
}
