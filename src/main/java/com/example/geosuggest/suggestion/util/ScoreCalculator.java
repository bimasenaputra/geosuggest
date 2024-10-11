package com.example.geosuggest.suggestion.util;

import java.util.ArrayList;
import java.util.List;

public class ScoreCalculator {
    private ScoreCalculator() {}

    /**
     * Normalizes a list of numeric values to a range between 0 and 1 based on the maximum value in the list.
     *
     * This method takes a list of numbers (e.g., integers, doubles) and scales them relative to the
     * maximum value found in the list. Each value is divided by the maximum plus one, ensuring that the largest
     * number in the list is represented as close to 1.0 and all other numbers are proportionally reduced
     * to a value between 0 and 1.
     *
     * @param numbers A list of numeric values to be normalized. This can include any subclass of
     *                {@link Number} (e.g., Integer, Double).
     * @return A list of normalized values, where each value is between 0 and 1. The order of the
     *         returned list corresponds to the order of the input list.
     */
    public static List<Double> maxNormalized(List<? extends Number> numbers) {
        // Find the maximum value
        double max = 0;
        for (Number num : numbers) {
            max = Math.max(max, num.doubleValue());
        }

        // Normalize the numbers
        List<Double> scores = new ArrayList<>();
        for (Number num : numbers) {
            scores.add(num.doubleValue() / (max + 1));
        }

        return scores;
    }
}
