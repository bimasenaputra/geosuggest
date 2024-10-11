package com.example.geosuggest.suggestion;

import com.example.geosuggest.suggestion.util.ScoreCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    private SuggestionDAO suggestionDAO;

    /**
     * Retrieves a list of city suggestions based on the search query, sorted by population in descending order.
     *
     * This method finds all city names starting with the given query prefix and sorts them by population
     * (from largest to smallest). It then calculates a normalized score for each city based on its population,
     * and returns a list of {@link SuggestionDTO} objects containing the city name, latitude, longitude, and score.
     *
     * @param query The search query representing the prefix of the city names to search for.
     *              The query is case-insensitive and can be partial (e.g., "Tor" can match "Toronto").
     * @return An iterable list of {@link SuggestionDTO} objects containing the city name, latitude, longitude,
     *         and a population-based score. If no cities are found matching the query, the method returns an empty list.
     */
    @Override
    public Iterable<SuggestionDTO> getCitiesSuggestions(String query) {
        List<String> cityNames = suggestionDAO.getCitiesStartingWithPrefixSortedByPopulation(query);

        List<Long> cityPopulations = new ArrayList<>();
        for (String city : cityNames) {
            cityPopulations.add(suggestionDAO.getCityPopulation(city));
        }

        List<Double> scores = ScoreCalculator.maxNormalized(cityPopulations);

        List<SuggestionDTO> suggestions = new ArrayList<>();
        for (int i = 0; i < cityNames.size(); ++i) {
            String city = cityNames.get(i);
            SuggestionDTO dto = new SuggestionDTO();
            dto.setName(city);
            dto.setLatitude(suggestionDAO.getCityLatitude(city));
            dto.setLongitude(suggestionDAO.getCityLongitude(city));
            dto.setScore(scores.get(i));
            suggestions.add(dto);
        }

        return suggestions;
    }

    /**
     * Retrieves a list of city suggestions based on the search query and geographical proximity.
     *
     * This method finds all city names starting with the given query prefix and sorts them by their distance
     * to the provided latitude and longitude coordinates. It calculates a normalized proximity score for each city
     * (where closer cities have higher scores) and returns a list of {@link SuggestionDTO} objects containing the
     * city name, latitude, longitude, and score.
     *
     * @param query     The search query representing the prefix of the city names to search for.
     *                  The query is case-insensitive and can be partial (e.g., "Van" can match "Vancouver").
     * @param latitude  The latitude coordinate used to calculate the proximity of the cities.
     * @param longitude The longitude coordinate used to calculate the proximity of the cities.
     * @return An iterable list of {@link SuggestionDTO} objects containing the city name, latitude, longitude,
     *         and a proximity-based score. The cities are sorted by proximity to the given coordinates.
     *         If no cities are found matching the query, the method returns an empty list.
     */
    @Override
    public Iterable<SuggestionDTO> getCitiesSuggestions(String query, double latitude, double longitude) {
        List<String> cityNames = suggestionDAO.getCitiesStartingWithPrefixNearest(query, latitude, longitude);

        List<Double> cityDistances = new ArrayList<>();
        for (String city : cityNames) {
            cityDistances.add(suggestionDAO.getCityRelativeDistance(city, latitude, longitude));
        }

        List<Double> scores = ScoreCalculator.maxNormalized(cityDistances);

        List<SuggestionDTO> suggestions = new ArrayList<>();
        for (int i = 0; i < cityNames.size(); ++i) {
            String city = cityNames.get(i);
            SuggestionDTO dto = new SuggestionDTO();
            dto.setName(city);
            dto.setLatitude(suggestionDAO.getCityLatitude(city));
            dto.setLongitude(suggestionDAO.getCityLongitude(city));
            dto.setScore(1 - scores.get(i));
            suggestions.add(dto);
        }

        return suggestions;
    }
}
