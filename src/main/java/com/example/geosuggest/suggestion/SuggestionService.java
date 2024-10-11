package com.example.geosuggest.suggestion;


public interface SuggestionService {
    Iterable<SuggestionDTO> getCitiesSuggestions(String query);
    Iterable<SuggestionDTO> getCitiesSuggestions(String query, double latitude, double longitude);
}