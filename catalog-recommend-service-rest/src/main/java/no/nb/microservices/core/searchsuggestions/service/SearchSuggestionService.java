package no.nb.microservices.core.searchsuggestions.service;

import no.nb.microservices.core.searchsuggestions.model.Suggestion;
import no.nb.microservices.core.searchsuggestions.repository.SearchSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Created by alfredw on 10/12/15.
 */
@Service
public class SearchSuggestionService implements ISearchSuggestionService {

    private final SearchSuggestionRepository searchSuggestionRepository;

    @Autowired
    public SearchSuggestionService(SearchSuggestionRepository searchSuggestionRepository) {
        this.searchSuggestionRepository = searchSuggestionRepository;
    }

    @Override
    public List<Suggestion> getSuggestions(String currentSite) {
        List<Suggestion> suggestions = searchSuggestionRepository.getSuggestions(currentSite);
        final long seed = System.nanoTime();
        List<Suggestion> tmp = new ArrayList<>(suggestions);
        Collections.shuffle(tmp, new Random(seed));
        return tmp;
    }
}
