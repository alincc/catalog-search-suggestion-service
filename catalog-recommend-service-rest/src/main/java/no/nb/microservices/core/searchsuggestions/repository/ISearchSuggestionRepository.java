package no.nb.microservices.core.searchsuggestions.repository;

import no.nb.microservices.core.searchsuggestions.model.Suggestion;

import java.util.List;

/**
 * Created by alfredw on 10/13/15.
 */

public interface ISearchSuggestionRepository {
    List<Suggestion> getSuggestions(String currentSite);
}
