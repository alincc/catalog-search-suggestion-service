package no.nb.microservices.catalogrecommend.core.searchsuggestions.repository;

import no.nb.microservices.catalogrecommend.core.searchsuggestions.model.Suggestion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Created by alfredw on 10/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchSuggestionRepositoryTest {

    private SearchSuggestionRepository searchSuggestionRepository;

    @Before
    public void setup() {
        searchSuggestionRepository = new SearchSuggestionRepository();
    }

    @Test
    public void getSearchSuggestionsForNbdigital() {
        List<Suggestion> suggestions = searchSuggestionRepository.getSuggestions("nbdigital");

        assertThat(suggestions, hasSize(19));
    }

    @Test
    public void getSearchSuggestionsForStatsmaktene() {
        List<Suggestion> suggestions = searchSuggestionRepository.getSuggestions("statsmaktene");

        assertThat(suggestions, hasSize(4));
    }

    @Test
    public void getSearchSuggestionsForSiteThatDontHaveSuggestions() {
        List<Suggestion> suggestions = searchSuggestionRepository.getSuggestions("site");

        assertThat(suggestions, hasSize(0));
    }
}
