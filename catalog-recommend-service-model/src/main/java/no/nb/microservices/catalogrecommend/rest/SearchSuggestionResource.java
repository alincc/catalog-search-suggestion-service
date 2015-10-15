package no.nb.microservices.catalogrecommend.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by alfredw on 10/12/15.
 */
public class SearchSuggestionResource extends ResourceSupport{
    private final String searchUrl;
    private final String imageUrl;
    private final String name;

    @JsonCreator
    public SearchSuggestionResource(@JsonProperty("searchUrl") String searchUrl,
                                    @JsonProperty("imageUrl") String imageUrl,
                                    @JsonProperty("name") String name) {
        this.searchUrl = searchUrl;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
