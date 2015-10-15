package no.nb.microservices.core.searchsuggestions.service;

import no.nb.microservices.core.searchsuggestions.model.Suggestion;

import java.util.List;

/**
 * Created by alfredw on 10/12/15.
 */
public interface ISearchSuggestionService {
    List<Suggestion> getSuggestions(String currentSite);
}


