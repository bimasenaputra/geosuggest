package com.example.geosuggest.suggestion;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class SuggestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SuggestionService suggestionService;

    @InjectMocks
    private SuggestionController suggestionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(suggestionController).build();
    }

    @Test
    public void testGetSuggestions_WithLatitudeAndLongitude() throws Exception {
        // Prepare mock data
        SuggestionDTO dto1 = new SuggestionDTO();
        dto1.setName("Toronto");
        dto1.setLatitude(43.7);
        dto1.setLongitude(-79.42);
        dto1.setScore(1.0);

        SuggestionDTO dto2 = new SuggestionDTO();
        dto2.setName("Tampa");
        dto2.setLatitude(27.95);
        dto2.setLongitude(-82.46);
        dto2.setScore(0.9);

        List<SuggestionDTO> mockSuggestions = Arrays.asList(dto1, dto2);

        // Mock the service call
        when(suggestionService.getCitiesSuggestions(anyString(), anyDouble(), anyDouble())).thenReturn(mockSuggestions);

        // Perform the request
        mockMvc.perform(get("/suggestions")
                        .param("q", "to")
                        .param("latitude", "43.7")
                        .param("longitude", "-79.42")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Toronto"))
                .andExpect(jsonPath("$[1].name").value("Tampa"));
    }

    @Test
    public void testGetSuggestions_WithoutLatitude() throws Exception {
        // Prepare mock data
        SuggestionDTO dto1 = new SuggestionDTO();
        dto1.setName("Toronto");
        dto1.setLatitude(43.7);
        dto1.setLongitude(-79.42);
        dto1.setScore(1.0);

        List<SuggestionDTO> mockSuggestions = Arrays.asList(dto1);

        // Mock the service call
        when(suggestionService.getCitiesSuggestions(anyString())).thenReturn(mockSuggestions);

        // Perform the request without latitude
        mockMvc.perform(get("/suggestions")
                        .param("q", "to"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSuggestions_WithoutLongitude() throws Exception {
        // Prepare mock data
        SuggestionDTO dto1 = new SuggestionDTO();
        dto1.setName("Toronto");
        dto1.setLatitude(43.7);
        dto1.setLongitude(-79.42);
        dto1.setScore(1.0);

        List<SuggestionDTO> mockSuggestions = Arrays.asList(dto1);

        // Mock the service call
        when(suggestionService.getCitiesSuggestions(anyString())).thenReturn(mockSuggestions);

        // Perform the request without longitude
        mockMvc.perform(get("/suggestions")
                        .param("q", "to"))
                .andExpect(status().isOk());
    }

    // Additional tests for edge cases can be added here
}