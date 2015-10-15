package no.nb.microservices.catalogrecommend.core.searchsuggestions.service;

import no.nb.microservices.catalogrecommend.core.searchsuggestions.model.Suggestion;

import java.util.List;

/**
 * Created by alfredw on 10/12/15.
 */
public interface ISearchSuggestionService {
    List<Suggestion> getSuggestions(String currentSite);
}


