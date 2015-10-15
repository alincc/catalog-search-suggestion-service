package no.nb.microservices.core.searchsuggestions.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alfredw on 10/13/15.
 */
public class Suggestion {
    private final String searchUrl;
    private final String imageUrl;
    private final String name;

    @JsonCreator
    public Suggestion(@JsonProperty("searchUrl") String searchUrl,
                      @JsonProperty("imageUrl") String imageUrl,
                      @JsonProperty("name") String name) {
        this.searchUrl = searchUrl;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getSearchUrl() { return searchUrl; }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
