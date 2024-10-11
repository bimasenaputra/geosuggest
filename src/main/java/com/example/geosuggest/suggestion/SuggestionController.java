package com.example.geosuggest.suggestion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Controller
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService =  suggestionService;
    }

    /**
     * Handles GET requests for city suggestions based on the input query and optional location (latitude and longitude).
     *
     * @param query     The search query for city names (required). The query is case-insensitive and partial matches are allowed.
     * @param latitude  The latitude coordinate for location-based filtering (optional).
     * @param longitude The longitude coordinate for location-based filtering (optional).
     * @return A ResponseEntity containing a list of city suggestions as {@link SuggestionDTO} objects in JSON format.
     *         Returns HTTP 400 Bad Request if either latitude or longitude is missing when provided.
     */
    @Operation(summary = "Get city suggestions based on input query",
            description = "Returns a list of city suggestions that match the given query, sorted by the population. " +
                    "If latitude and longitude are provided, suggestions may be sorted " +
                    "based on proximity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of city suggestions", content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SuggestionDTO.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = {"/suggestions"}, produces = {"application/json"})
    public ResponseEntity<Iterable<SuggestionDTO>> getSuggestions(
            @RequestParam(name = "q") String query,
            @RequestParam(name = "latitude", required = false) Double latitude,
            @RequestParam(name = "longitude", required = false) Double longitude) {
        // Capitalize words in the query for consistency (e.g., "new york" -> "New York")
        query =  StringUtils.capitalizeWords(query);

        // Check if latitude or longitude are not passed (null)
        if (latitude == null || longitude == null) {
            return ResponseEntity.ok(suggestionService.getCitiesSuggestions(query));
        }

        // Proceed with service call if latitude and longitude are present
        return ResponseEntity.ok(suggestionService.getCitiesSuggestions(query, latitude, longitude));
    }
}