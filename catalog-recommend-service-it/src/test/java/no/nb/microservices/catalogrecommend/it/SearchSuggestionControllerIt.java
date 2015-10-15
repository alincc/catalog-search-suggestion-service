package no.nb.microservices.catalogrecommend.it;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import no.nb.microservices.catalogrecommend.Application;
import no.nb.microservices.catalogrecommend.rest.model.SearchSuggestionResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, RibbonClientConfiguration.class})
@WebIntegrationTest("server.port: 0")
public class SearchSuggestionControllerIt {

    @Value("${local.server.port}")
    int port;

    @Autowired
    ILoadBalancer lb;

    MockWebServer server;
    private RestTemplate restTemplate;


    @Before
    public void setup() throws IOException {
        server = new MockWebServer();
        server.start();

        BaseLoadBalancer blb = (BaseLoadBalancer) lb;
        blb.setServersList(Arrays.asList(new Server(server.getHostName(), server.getPort())));

        restTemplate = new TestRestTemplate();
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void getSuggestionsForNbDigital() {
        String url = "http://localhost:" + port + "/catalog/nbdigital/suggestions";
        ResponseEntity<List<SearchSuggestionResource>> responseEntity = getResponse(url);

        assertThat(responseEntity.getBody(), hasSize(19));
    }

    @Test
    public void getSuggestionsForStatsmaktene() {
        String url = "http://localhost:" + port + "/catalog/statsmaktene/suggestions";
        ResponseEntity<List<SearchSuggestionResource>> responseEntity = getResponse(url);

        assertThat(responseEntity.getBody(), hasSize(4));
    }

    @Test
    public void getSuggestionsForSiteWithoutSuggestions() {
        String url = "http://localhost:" + port + "/catalog/site/suggestions";
        ResponseEntity<List<SearchSuggestionResource>> responseEntity = getResponse(url);

        assertThat(responseEntity.getBody(), hasSize(0));
    }

    public ResponseEntity<List<SearchSuggestionResource>> getResponse(String url) {
        ParameterizedTypeReference<List<SearchSuggestionResource>> parameterizedTypeReference = new ParameterizedTypeReference<List<SearchSuggestionResource>>() { };
        return restTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference);
    }
}

@Configuration
class RibbonClientConfiguration {

    @Bean
    public ILoadBalancer ribbonLoadBalancer() {
        return new BaseLoadBalancer();
    }
}
