package com.example.geosuggest.suggestion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeoNameTest {

    private GeoName geoName;

    @BeforeEach
    public void setUp() {
        geoName = new GeoName();
    }

    @Test
    public void testSetName() {
        String name = "Toronto, Ontario, Canada";
        geoName.setName(name);
        assertEquals(name, geoName.getName());
    }

    @Test
    public void testSetLatitude() {
        double latitude = 43.65107;
        geoName.setLatitude(latitude);
        assertEquals(latitude, geoName.getLatitude(), 0.00001);
    }

    @Test
    public void testSetLongitude() {
        double longitude = -79.347015;
        geoName.setLongitude(longitude);
        assertEquals(longitude, geoName.getLongitude(), 0.00001);
    }

    @Test
    public void testSetPopulation() {
        long population = 2731571L;
        geoName.setPopulation(population);
        assertEquals(population, geoName.getPopulation());
    }

    @Test
    public void testGeoNameFullSetters() {
        String name = "Montreal, Quebec, Canada";
        double latitude = 45.50884;
        double longitude = -73.58781;
        long population = 1704694L;

        geoName.setName(name);
        geoName.setLatitude(latitude);
        geoName.setLongitude(longitude);
        geoName.setPopulation(population);

        assertEquals(name, geoName.getName());
        assertEquals(latitude, geoName.getLatitude(), 0.00001);
        assertEquals(longitude, geoName.getLongitude(), 0.00001);
        assertEquals(population, geoName.getPopulation());
    }
}