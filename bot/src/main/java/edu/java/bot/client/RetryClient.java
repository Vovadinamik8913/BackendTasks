package edu.java.bot.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RetryClient {
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    public <T> ResponseEntity<T> getWithRetry(String uri, Class<T> clazz) {
        return retryTemplate.execute(context ->
        {
            try {
                return restTemplate.getForEntity(uri, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T> ResponseEntity<T> exchangeWithRetry(
        String url,
        HttpMethod method,
        HttpEntity<?> request,
        Class<T> clazz
    ) {
        return retryTemplate.execute(context ->
        {
            try {
                return restTemplate.exchange(url, method, request, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T> ResponseEntity<T> postWithRetry(
        String url,
        HttpEntity<?> request,
        Class<T> clazz
    ) {
        return retryTemplate.execute(context ->
        {
            try {
                return restTemplate.postForEntity(url, request, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
