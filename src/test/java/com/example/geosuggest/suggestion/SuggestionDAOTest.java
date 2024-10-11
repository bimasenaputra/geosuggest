package com.example.geosuggest.suggestion;

import com.example.geosuggest.suggestion.trie.Trie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

public class SuggestionDAOTest {

    @InjectMocks
    private SuggestionDAO suggestionDAO; // Injects mocks into SuggestionDAO

    @Spy
    private SuggestionDAO suggestionDAOSpy; // Declare a spy for SuggestionDAO

    @BeforeEach
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize suggestionDAO instance
        suggestionDAO = new SuggestionDAO();

        // Create a spy on the suggestionDAO
        suggestionDAOSpy = spy(suggestionDAO);

        // Mock loadCities to do nothing
        doNothing().when(suggestionDAOSpy).loadCities();

        loadMockData(suggestionDAOSpy); // Load mock data into the spy

        this.suggestionDAO = suggestionDAOSpy; // Set the spyDAO as the suggestionDAO instance
    }

    private void loadMockData(SuggestionDAO dao) throws IOException, NoSuchFieldException, IllegalAccessException {
        String mockData = "City\tCountry\tRegion\tPopulation\tLatitude\tLongitude\n"
                + "Toronto\tCanada\tOntario\t3000000\t43.7\t-79.42\n"
                + "Tampa\tUSA\tFlorida\t400000\t27.95\t-82.46\n"
                + "Vancouver\tCanada\tBritish Columbia\t2300000\t49.28\t-123.12\n"
                + "Victoria\tCanada\tBritish Columbia\t85000\t48.43\t-123.37\n"
                + "Calgary\tCanada\tAlberta\t1300000\t51.04\t-114.07\n"
                + "Montreal\tCanada\tQuebec\t1700000\t45.5\t-73.56\n";

        BufferedReader reader = new BufferedReader(new StringReader(mockData));

        // Directly insert mock data into the Trie and cities map
        String line;
        // Skip the first line
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split("\t");
            String name = fields[0]; // City name
            double latitude = Double.parseDouble(fields[4]);
            double longitude = Double.parseDouble(fields[5]);
            long population = Long.parseLong(fields[3]);
            String country = fields[1];
            String province = fields[2];
            String administrativeDivision = province + ", " + country;
            String fullName = name + ", " + administrativeDivision;

            // Accessing the citiesTrie field using reflection
            Field trieField = SuggestionDAO.class.getDeclaredField("citiesTrie");
            trieField.setAccessible(true); // Make the field accessible
            Trie citiesTrie = (Trie) trieField.get(dao); // Get the Trie instance
            citiesTrie.insert(fullName); // Insert city name into the Trie

            // Accessing the cities field using reflection
            Field citiesField = SuggestionDAO.class.getDeclaredField("cities");
            citiesField.setAccessible(true); // Make the field accessible
            @SuppressWarnings("unchecked") // Suppress unchecked warning for raw type
            Map<String, GeoName> cities = (Map<String, GeoName>) citiesField.get(dao); // Get the cities map
            GeoName geoName = new GeoName();
            geoName.setName(fullName);
            geoName.setLatitude(latitude);
            geoName.setLongitude(longitude);
            geoName.setPopulation(population);
            cities.putIfAbsent(fullName, geoName); // Insert GeoName into cities map
        }
    }

    @Test
    public void testGetCitiesStartingWithPrefixSortedByPopulation() {
        List<String> cities = suggestionDAO.getCitiesStartingWithPrefixSortedByPopulation("T");
        assertEquals(2, cities.size());
        assertEquals("Toronto, Ontario, Canada", cities.get(0)); // Highest population
        assertEquals("Tampa, Florida, USA", cities.get(1));
    }

    @Test
    public void testGetCitiesStartingWithPrefixNearest() {
        List<String> cities = suggestionDAO.getCitiesStartingWithPrefixNearest("V", 49.25, -123.12);
        assertEquals(2, cities.size());
        assertEquals("Vancouver, British Columbia, Canada", cities.get(0));
        assertEquals("Victoria, British Columbia, Canada", cities.get(1));
    }

    @Test
    public void testGetCityPopulation() {
        long population = suggestionDAO.getCityPopulation("Toronto, Ontario, Canada");
        assertEquals(3000000L, population);
    }

    @Test
    public void testGetCityLatitude() {
        double latitude = suggestionDAO.getCityLatitude("Calgary, Alberta, Canada");
        assertEquals(51.04, latitude);
    }

    @Test
    public void testGetCityLongitude() {
        double longitude = suggestionDAO.getCityLongitude("Montreal, Quebec, Canada");
        assertEquals(-73.56, longitude);
    }

    @Test
    public void testGetCityRelativeDistance() {
        double distance = suggestionDAO.getCityRelativeDistance("Toronto, Ontario, Canada", 49.25, -123.12);
        assertTrue(distance >= 0); // Distance should be non-negative
    }
}
