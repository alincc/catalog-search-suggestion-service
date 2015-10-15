package no.nb.microservices.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import no.nb.microservices.catalogrecommend.rest.SearchSuggestionResource;
import no.nb.microservices.core.searchsuggestions.model.Suggestion;
import no.nb.microservices.core.searchsuggestions.service.ISearchSuggestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "/catalog/", description = "Home api")
@RequestMapping(value = "/catalog/")
public class SearchSuggestionController {

    private static final Logger LOG = LoggerFactory.getLogger(SearchSuggestionController.class);

    private final ISearchSuggestionService searchSuggestionService;

    @Autowired
    public SearchSuggestionController(ISearchSuggestionService searchSuggestionService) {
        this.searchSuggestionService = searchSuggestionService;
    }

    @ApiOperation(value = "Hello World", notes = "Hello World notes", response = String.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response") })
    @RequestMapping(value = "{currentSite}/suggestions", method = RequestMethod.GET)
    public ResponseEntity<List<SearchSuggestionResource>> getSuggestions(@PathVariable(value = "currentSite") String currentSite) {
        List<Suggestion> suggestions = searchSuggestionService.getSuggestions(currentSite);

        if(suggestions != null) {
            List<SearchSuggestionResource> body = suggestions.stream()
                    .map(suggestion -> new SearchSuggestionResource(suggestion.getSearchUrl(), suggestion.getImageUrl(), suggestion.getName()))
                    .collect(Collectors.toList());
            return new ResponseEntity<List<SearchSuggestionResource>>(body, HttpStatus.OK);
        }
        return new ResponseEntity<List<SearchSuggestionResource>>(new ArrayList<>(), HttpStatus.OK);
    }
}
