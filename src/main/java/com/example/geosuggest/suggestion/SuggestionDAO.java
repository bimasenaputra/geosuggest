package com.example.geosuggest.suggestion;

import com.example.geosuggest.suggestion.trie.Trie;
import com.example.geosuggest.suggestion.util.FIPSMapper;
import com.example.geosuggest.suggestion.util.GeoCalculator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SuggestionDAO {
    private final String DATA_SOURCE = "src/main/resources/geonames/cities_canada-usa.tsv";
    private Trie citiesTrie = new Trie();
    private Map<String, GeoName> cities = new HashMap<String, GeoName>();

    /**
     * Loads city data from a TSV file into the in-memory Trie structure and a map for efficient search operations.
     *
     * This method reads a TSV (Tab Separated Values) file containing city data, where each line represents a city's
     * information (e.g., name, latitude, longitude, population, country, and administrative region). The first line
     * (header) is skipped. Each city's name is inserted into a Trie for efficient prefix-based searches. Additionally,
     * the city's geographical and population data is stored in a {@link GeoName} object and placed into a map for
     * quick lookup during search operations.
     *
     * The expected TSV file format is as follows (0-indexed):
     * - Column 1: City name
     * - Column 4: Latitude
     * - Column 5: Longitude
     * - Column 14: Population
     * - Column 8: Country
     * - Column 10: Administrative division (e.g., province, state)
     *
     * @throws IOException if an error occurs while reading the TSV file.
     */
    @PostConstruct
    public void loadCities() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_SOURCE))) {
            String line;

            // Skip the first line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                String name = fields[1];
                double latitude = Double.parseDouble(fields[4]);
                double longitude = Double.parseDouble(fields[5]);
                long population = Long.parseLong(fields[14]);
                String country = fields[8];
                String province = FIPSMapper.getRegionByFIPS(fields[10]);
                String administrativeDivision = province + ", " + country;
                String fullName = name + ", " + administrativeDivision;
                this.citiesTrie.insert(fullName);

                GeoName geoName = new GeoName();
                geoName.setName(fullName);
                geoName.setLatitude(latitude);
                geoName.setLongitude(longitude);
                geoName.setPopulation(population);
                this.cities.putIfAbsent(fullName, geoName);
            }
        }
    }

    /**
     * Retrieves a list of city names that start with the given prefix, sorted by population in descending order.
     *
     * This method searches the Trie for all city names that start with the specified prefix (case-insensitive),
     * then retrieves the corresponding cities from the in-memory map and sorts them by their population size
     * (from largest to smallest). The sorted list of city names is returned.
     *
     * @param prefix The search query representing the starting characters of the city names.
     *               This query is case-insensitive and may be a partial match (e.g., "Tor" for "Toronto").
     * @return A list of city names that start with the given prefix, sorted by population in descending order.
     *         If no cities are found matching the prefix, the method returns an empty list.
     */
    public List<String> getCitiesStartingWithPrefixSortedByPopulation(String prefix) {
        List<String> cityNames = this.citiesTrie.getLettersStartingWith(prefix);

        cityNames.sort((city1, city2) -> {
            GeoName geo1 = cities.get(city1);
            GeoName geo2 = cities.get(city2);
            return Long.compare(geo2.getPopulation(), geo1.getPopulation()); // Descending order
        });

        return cityNames;
    }

    /**
     * Retrieves a list of city names that start with the given prefix, sorted by their proximity to the specified coordinates.
     *
     * This method searches the Trie for all city names that start with the specified prefix (case-insensitive).
     * For each matching city, it calculates the geographical distance to the provided latitude and longitude using the Haversine formula.
     * The list of city names is then sorted by their proximity to the given coordinates, with the nearest cities appearing first.
     *
     * @param prefix    The search query representing the starting characters of the city names.
     *                  This query is case-insensitive and may be a partial match (e.g., "Van" for "Vancouver").
     * @param latitude  The latitude coordinate used to calculate the distance from each city.
     * @param longitude The longitude coordinate used to calculate the distance from each city.
     * @return A list of city names that start with the given prefix, sorted by their proximity to the provided coordinates.
     *         If no cities are found matching the prefix, the method returns an empty list.
     */
    public List<String> getCitiesStartingWithPrefixNearest(String prefix, double latitude, double longitude) {
        List<String> cityNames = this.citiesTrie.getLettersStartingWith(prefix);

        cityNames.sort((city1, city2) -> {
            GeoName geo1 = cities.get(city1);
            GeoName geo2 = cities.get(city2);
            double distance1 = GeoCalculator.haversineDistance(latitude, longitude, geo1.getLatitude(), geo1.getLongitude());
            double distance2 = GeoCalculator.haversineDistance(latitude, longitude, geo2.getLatitude(), geo2.getLongitude());
            return Double.compare(distance1, distance2); // Ascending order
        });

        return cityNames;
    }

    /**
     * Retrieves the population of the specified city.
     *
     * This method looks up the city in the in-memory map and returns the population of the city,
     * if it exists in the dataset.
     *
     * @param city The full name of the city (including administrative division) for which the population is requested.
     * @return The population of the specified city.
     *         If the city is not found, this method may throw a {@link NullPointerException}.
     */
    public long getCityPopulation(String city) {
        return cities.get(city).getPopulation();
    }

    /**
     * Retrieves the latitude coordinate of the specified city.
     *
     * This method looks up the city in the in-memory map and returns the latitude of the city,
     * if it exists in the dataset.
     *
     * @param city The full name of the city (including administrative division) for which the latitude is requested.
     * @return The latitude of the specified city.
     *         If the city is not found, this method may throw a {@link NullPointerException}.
     */
    public double getCityLatitude(String city)  {
        return cities.get(city).getLatitude();
    }

    /**
     * Retrieves the longitude coordinate of the specified city.
     *
     * This method looks up the city in the in-memory map and returns the longitude of the city,
     * if it exists in the dataset.
     *
     * @param city The full name of the city (including administrative division) for which the longitude is requested.
     * @return The longitude of the specified city.
     *         If the city is not found, this method may throw a {@link NullPointerException}.
     */
    public double getCityLongitude(String city) {
        return cities.get(city).getLongitude();
    }

    /**
     * Calculates the relative distance between the specified city and a given geographical point
     * defined by latitude and longitude.
     *
     * This method retrieves the latitude and longitude of the specified city from the in-memory dataset
     * and uses the Haversine formula to compute the great-circle distance to the provided coordinates.
     * The result represents the distance in kilometers.
     *
     * @param city     The full name of the city (including administrative division) for which the distance is calculated.
     * @param latitude The latitude of the reference point to which the distance is calculated.
     * @param longitude The longitude of the reference point to which the distance is calculated.
     * @return The distance in kilometers between the specified city and the given latitude/longitude.
     *         If the city is not found, this method may throw a {@link NullPointerException}.
     */
    public double getCityRelativeDistance(String city, double latitude, double longitude) {
        double cityLatitude = getCityLatitude(city);
        double cityLongitude = getCityLongitude(city);
        return GeoCalculator.haversineDistance(latitude, longitude, cityLatitude, cityLongitude);
    }
}