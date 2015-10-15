package no.nb.microservices.catalogrecommend.core.searchsuggestions.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nb.microservices.catalogrecommend.core.searchsuggestions.model.Suggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfredw on 10/13/15.
 */
@Repository
public class SearchSuggestionRepository implements ISearchSuggestionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SearchSuggestionRepository.class);
    public static final String SEARCH_SUGGESTION_FILE_NAME = "searchSuggestion.json";
    private Map<String, List<Suggestion>> suggestions;

    public SearchSuggestionRepository() {
        try {
            loadSuggestionsFromFile();
        } catch (IOException e) {
            suggestions = new HashMap<>();
            LOG.info("Problem loading suggestion file '" + SEARCH_SUGGESTION_FILE_NAME + "'");
            LOG.debug("Problem loading suggestion file '" + SEARCH_SUGGESTION_FILE_NAME + "'", e);
        }
    }

    private void loadSuggestionsFromFile() throws IOException {
        ClassPathResource resource = new ClassPathResource(SEARCH_SUGGESTION_FILE_NAME);
        File file = resource.getFile();
        String json = new String(Files.readAllBytes(Paths.get(file.toURI())));

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<String,List<Suggestion>>> typeRef = new TypeReference<HashMap<String, List<Suggestion>>>() {};
        suggestions = objectMapper.readValue(json, typeRef);
    }

    @Override
    public List<Suggestion> getSuggestions(String currentSite) {
        if(suggestions.containsKey(currentSite)) {
            return suggestions.get(currentSite);
        } else {
            LOG.info("Don't have any suggestions for site '" + currentSite + "'");
            return new ArrayList<>();
        }
    }
}
