package edu.java.scrapper.service;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.model.github.GitClient;
import edu.java.scrapper.model.github.repository.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@WireMockTest
@ExtendWith(WireMockExtension.class)
class GitClientTest {
    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort())
        .build();

    private GitClient createClient() {

        WebClient webClient = WebClient.builder().baseUrl(wireMock.baseUrl()).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient)).build();
        return factory.createClient(GitClient.class);
    }

    @Test
    void testGetRepository() {
        wireMock.stubFor(get(urlEqualTo("/repos/octocat/Hello-World"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                            {
                                "name": "Hello-World",
                                "full_name": "octocat/Hello-World",
                                "description": "My first repository",
                                "html_url": "https://github.com/octocat/Hello-World",
                                "updated_at": "2024-01-01T10:00:00Z",
                                "pushed_at": "2024-01-02T12:30:45Z"
                            }
                            """)));

        GitClient client = createClient();
        Repository repository = client.getRepository("octocat", "Hello-World");
        Assertions.assertEquals("Hello-World", repository.getName());
        Assertions.assertEquals("My first repository", repository.getDescription());
    }
}
