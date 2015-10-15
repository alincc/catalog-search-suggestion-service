package no.nb.microservices.catalogrecommend;

import no.nb.microservices.catalogrecommend.rest.model.SearchSuggestionResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest("server.port: 0")
public class SearchSuggestionControllerTest {

    @Value("${local.server.port}")
    int port;

    private RestTemplate restTemplate;

    @Before
    public void setup() throws IOException {
        restTemplate = new TestRestTemplate();
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
        ParameterizedTypeReference<List<SearchSuggestionResource>> parameterizedTypeReference = new ParameterizedTypeReference<List<SearchSuggestionResource>>() {
        };
        return restTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference);
    }
}
