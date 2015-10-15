package no.nb.microservices.core.searchsuggestions.service;

import no.nb.microservices.core.searchsuggestions.model.Suggestion;
import no.nb.microservices.core.searchsuggestions.repository.SearchSuggestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

/**
 * Created by alfredw on 10/14/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchSuggestionServiceTest {

    private static final String CURRENT_SITE = "nbdigital";

    @Mock
    private SearchSuggestionRepository searchSuggestionRepository;

    @InjectMocks
    private SearchSuggestionService searchSuggestionService;

    @Before
    public void setup() {
        List<Suggestion> suggestions = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            suggestions.add(new Suggestion("searchUrl " + i, "imageUrl " + i, "name " + i));
        }
        when(searchSuggestionRepository.getSuggestions(CURRENT_SITE)).thenReturn(suggestions);
    }

    @Test
    public void testWithMultipleUsers() {
        int numOfRuns = 10000;
        int numOfUsers = 15;

        List<TestUser> testUsers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < numOfUsers; i++) {
            TestUser testUser = new TestUser(numOfRuns);
            testUsers.add(testUser);
            Thread thread = new Thread(testUser);
            thread.start();
            threads.add(thread);
        }

        assertThatThereIsNoDuplicatesInSuggestionList(testUsers, threads);
    }

    private void assertThatThereIsNoDuplicatesInSuggestionList(List<TestUser> testUsers, List<Thread> threads) {
        for (int i = 0; i < testUsers.size(); i++) {
            while(threads.get(i).isAlive());
            assertFalse("Expected no duplicates in suggestionlist", testUsers.get(i).containsDuplicates());
        }
    }

    private class TestUser implements Runnable {

        private int numOfRuns;
        private boolean containsDuplicates = false;

        public TestUser(int numOfRuns) {
            this.numOfRuns = numOfRuns;
        }

        @Test
        @Override
        public void run() {
            for(int i = 0; (i < numOfRuns && !containsDuplicates); i++) {
                List<Suggestion> suggestions = searchSuggestionService.getSuggestions(CURRENT_SITE);
                containsDuplicates = containsDuplicates(suggestions);
            }
        }

        private boolean containsDuplicates(List<Suggestion> suggestions) {
            for(int i = 0; i < suggestions.size(); i++) {
                Suggestion current = suggestions.get(i);
                for(int j = 0; (i != j) && (j < suggestions.size()); j++) {
                    Suggestion tmp = suggestions.get(j);
                    if(current == tmp) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean containsDuplicates() {
            return containsDuplicates;
        }
    }
}
