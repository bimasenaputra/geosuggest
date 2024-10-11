package com.example.geosuggest.suggestion.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GeoCalculatorTest {

    @Test
    public void testHaversineDistance_sameLocation() {
        // Coordinates for the same location (Toronto)
        double lat1 = 43.65107;
        double lon1 = -79.347015;

        // Expect distance to be zero
        assertEquals(0.0, GeoCalculator.haversineDistance(lon1, lat1, lon1, lat1), 0.0001);
    }

    @Test
    public void testHaversineDistance_differentLocations() {
        // Coordinates for Toronto
        double lat1 = 43.65107;
        double lon1 = -79.347015;

        // Coordinates for Montreal
        double lat2 = 45.50884;
        double lon2 = -73.58781;

        // Expected distance between Toronto and Montreal (approx. 500 km)
        double expectedDistance = 500.0;

        // Verify distance with a small delta
        assertEquals(expectedDistance, GeoCalculator.haversineDistance(lon1, lat1, lon2, lat2), 1.0);
    }

    @Test
    public void testHaversineDistance_largeDistance() {
        // Coordinates for New York City
        double lat1 = 40.712776;
        double lon1 = -74.005974;

        // Coordinates for Los Angeles
        double lat2 = 34.052235;
        double lon2 = -118.243683;

        // Expected distance between NYC and LA (approx. 3936 km)
        double expectedDistance = 3936.0;

        // Verify distance with a small delta
        assertEquals(expectedDistance, GeoCalculator.haversineDistance(lon1, lat1, lon2, lat2), 10.0);
    }

    @Test
    public void testHaversineDistance_smallDistance() {
        // Coordinates for two points in Central Park, New York City
        double lat1 = 40.785091;
        double lon1 = -73.968285;

        double lat2 = 40.785091;
        double lon2 = -73.968500;

        // Expected small distance between the two points (approx. 0.018 km)
        double expectedDistance = 0.018;

        // Verify distance with a small delta
        assertEquals(expectedDistance, GeoCalculator.haversineDistance(lon1, lat1, lon2, lat2), 0.001);
    }
}

