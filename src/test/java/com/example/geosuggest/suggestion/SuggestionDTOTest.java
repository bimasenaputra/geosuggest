package com.example.geosuggest.suggestion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuggestionDTOTest {

    private SuggestionDTO suggestionDTO;

    @BeforeEach
    public void setUp() {
        suggestionDTO = new SuggestionDTO();
    }

    @Test
    public void testSetName() {
        String name = "Toronto, Ontario, Canada";
        suggestionDTO.setName(name);
        assertEquals(name, suggestionDTO.getName());
    }

    @Test
    public void testSetLatitude() {
        double latitude = 43.65107;
        suggestionDTO.setLatitude(latitude);
        assertEquals(latitude, suggestionDTO.getLatitude(), 0.00001);
    }

    @Test
    public void testSetLongitude() {
        double longitude = -79.347015;
        suggestionDTO.setLongitude(longitude);
        assertEquals(longitude, suggestionDTO.getLongitude(), 0.00001);
    }

    @Test
    public void testSetScore() {
        double score = 0.85;
        suggestionDTO.setScore(score);
        assertEquals(score, suggestionDTO.getScore(), 0.00001);
    }

    @Test
    public void testSuggestionDTOFullSetters() {
        String name = "Montreal, Quebec, Canada";
        double latitude = 45.50884;
        double longitude = -73.58781;
        double score = 0.90;

        suggestionDTO.setName(name);
        suggestionDTO.setLatitude(latitude);
        suggestionDTO.setLongitude(longitude);
        suggestionDTO.setScore(score);

        assertEquals(name, suggestionDTO.getName());
        assertEquals(latitude, suggestionDTO.getLatitude(), 0.00001);
        assertEquals(longitude, suggestionDTO.getLongitude(), 0.00001);
        assertEquals(score, suggestionDTO.getScore(), 0.00001);
    }
}