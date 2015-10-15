package no.nb.microservices.catalogrecommend.rest.controller;

import no.nb.microservices.catalogrecommend.rest.model.SearchSuggestionResource;
import no.nb.microservices.catalogrecommend.core.searchsuggestions.model.Suggestion;
import no.nb.microservices.catalogrecommend.core.searchsuggestions.service.ISearchSuggestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchSuggestionControllerTest {

    private static final String CURRENT_SITE = "nbdigital";

    @Mock
    private ISearchSuggestionService searchSuggestionService;

    @InjectMocks
    private SearchSuggestionController searchSuggestionController;

    @Test
    public void getSuggestionsForNbdigital() throws Exception {
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(new Suggestion("searchUrl1", "imageUrl1", "suggestion1Name"));
        suggestions.add(new Suggestion("searchUrl2", "imageUrl2", "suggestion2Name"));
        when(searchSuggestionService.getSuggestions(CURRENT_SITE)).thenReturn(suggestions);

        ResponseEntity<List<SearchSuggestionResource>> response = searchSuggestionController.getSuggestions(CURRENT_SITE);

        assertThat(response.getBody(), hasSize(2));
    }
}
