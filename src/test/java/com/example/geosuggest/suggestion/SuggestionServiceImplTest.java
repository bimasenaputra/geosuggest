package com.example.geosuggest.suggestion;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Arrays;
import com.example.geosuggest.suggestion.util.ScoreCalculator;

public class SuggestionServiceImplTest {

    @Mock
    private SuggestionDAO suggestionDAO;

    @InjectMocks
    private SuggestionServiceImpl suggestionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCitiesSuggestions_noLatLong() {
        // Mock data
        String query = "par";
        List<String> mockCityNames = Arrays.asList("Paris", "Parma", "Park City");
        List<Long> mockPopulations = Arrays.asList(2000000L, 196000L, 9000L);
        List<Double> mockScores = Arrays.asList(1.0, 0.5, 0.1);

        // Mock behavior
        when(suggestionDAO.getCitiesStartingWithPrefixSortedByPopulation(query)).thenReturn(mockCityNames);
        when(suggestionDAO.getCityPopulation("Paris")).thenReturn(mockPopulations.get(0));
        when(suggestionDAO.getCityPopulation("Parma")).thenReturn(mockPopulations.get(1));
        when(suggestionDAO.getCityPopulation("Park City")).thenReturn(mockPopulations.get(2));
        when(suggestionDAO.getCityLatitude(anyString())).thenReturn(48.8566);
        when(suggestionDAO.getCityLongitude(anyString())).thenReturn(2.3522);
        MockedStatic<ScoreCalculator> mocked = mockStatic(ScoreCalculator.class);  // Mock static methods
        when(ScoreCalculator.maxNormalized(mockPopulations)).thenReturn(mockScores);

        // Call the method to test
        Iterable<SuggestionDTO> suggestions = suggestionService.getCitiesSuggestions(query);

        // Verify results
        assertNotNull(suggestions);
        List<SuggestionDTO> suggestionList = (List<SuggestionDTO>) suggestions;
        assertEquals(3, suggestionList.size());

        // Check each suggestion
        SuggestionDTO suggestion1 = suggestionList.get(0);
        assertEquals("Paris", suggestion1.getName());
        assertEquals(48.8566, suggestion1.getLatitude());
        assertEquals(2.3522, suggestion1.getLongitude());
        assertEquals(1.0, suggestion1.getScore());

        SuggestionDTO suggestion2 = suggestionList.get(1);
        assertEquals("Parma", suggestion2.getName());
        assertEquals(0.5, suggestion2.getScore());

        SuggestionDTO suggestion3 = suggestionList.get(2);
        assertEquals("Park City", suggestion3.getName());
        assertEquals(0.1, suggestion3.getScore());

        mocked.close();
    }

    @Test
    public void testGetCitiesSuggestions_withLatLong() {
        // Mock data
        String query = "par";
        double latitude = 48.8566;
        double longitude = 2.3522;
        List<String> mockCityNames = Arrays.asList("Paris", "Parma", "Park City");
        List<Double> mockDistances = Arrays.asList(10.0, 50.0, 100.0);
        List<Double> mockScores = Arrays.asList(1.0, 0.8, 0.6);

        // Mock behavior
        when(suggestionDAO.getCitiesStartingWithPrefixNearest(query, latitude, longitude)).thenReturn(mockCityNames);
        when(suggestionDAO.getCityRelativeDistance("Paris", latitude, longitude)).thenReturn(mockDistances.get(0));
        when(suggestionDAO.getCityRelativeDistance("Parma", latitude, longitude)).thenReturn(mockDistances.get(1));
        when(suggestionDAO.getCityRelativeDistance("Park City", latitude, longitude)).thenReturn(mockDistances.get(2));
        when(suggestionDAO.getCityLatitude(anyString())).thenReturn(48.8566);
        when(suggestionDAO.getCityLongitude(anyString())).thenReturn(2.3522);
        MockedStatic<ScoreCalculator> mocked = mockStatic(ScoreCalculator.class);  // Mock static methods
        when(ScoreCalculator.maxNormalized(mockDistances)).thenReturn(mockScores);

        // Call the method to test
        Iterable<SuggestionDTO> suggestions = suggestionService.getCitiesSuggestions(query, latitude, longitude);

        // Verify results
        assertNotNull(suggestions);
        List<SuggestionDTO> suggestionList = (List<SuggestionDTO>) suggestions;
        assertEquals(3, suggestionList.size());

        // Check each suggestion
        SuggestionDTO suggestion1 = suggestionList.get(0);
        assertEquals("Paris", suggestion1.getName());
        assertEquals(48.8566, suggestion1.getLatitude());
        assertEquals(2.3522, suggestion1.getLongitude());
        assertEquals(1 - 1.0, suggestion1.getScore());  // Score is 1 - normalized score

        SuggestionDTO suggestion2 = suggestionList.get(1);
        assertEquals("Parma", suggestion2.getName());
        assertEquals(1 - 0.8, suggestion2.getScore());

        SuggestionDTO suggestion3 = suggestionList.get(2);
        assertEquals("Park City", suggestion3.getName());
        assertEquals(1 - 0.6, suggestion3.getScore());

        mocked.close();
    }
}
