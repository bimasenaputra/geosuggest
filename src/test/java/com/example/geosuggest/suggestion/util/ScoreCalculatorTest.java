package com.example.geosuggest.suggestion.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class ScoreCalculatorTest {

    @Test
    public void testMaxNormalized_withPositiveNumbers() {
        List<Integer> numbers = Arrays.asList(10, 20, 30);

        // Expected normalization: (10 / 31), (20 / 31), (30 / 31)
        List<Double> expectedScores = Arrays.asList(10.0 / 31, 20.0 / 31, 30.0 / 31);

        List<Double> actualScores = ScoreCalculator.maxNormalized(numbers);

        // Verify each normalized score
        for (int i = 0; i < expectedScores.size(); i++) {
            assertEquals(expectedScores.get(i), actualScores.get(i), 0.0001);
        }
    }

    @Test
    public void testMaxNormalized_withMixedNumbers() {
        List<Double> numbers = Arrays.asList(5.0, 15.0, -10.0, 20.0);

        // Expected normalization: (5 / 21), (15 / 21), (-10 / 21), (20 / 21)
        List<Double> expectedScores = Arrays.asList(5.0 / 21, 15.0 / 21, -10.0 / 21, 20.0 / 21);

        List<Double> actualScores = ScoreCalculator.maxNormalized(numbers);

        // Verify each normalized score
        for (int i = 0; i < expectedScores.size(); i++) {
            assertEquals(expectedScores.get(i), actualScores.get(i), 0.0001);
        }
    }

    @Test
    public void testMaxNormalized_withAllZeroNumbers() {
        List<Integer> numbers = Arrays.asList(0, 0, 0);

        // All zeros should result in zero normalization
        List<Double> expectedScores = Arrays.asList(0.0, 0.0, 0.0);

        List<Double> actualScores = ScoreCalculator.maxNormalized(numbers);

        // Verify each normalized score
        assertEquals(expectedScores, actualScores);
    }

    @Test
    public void testMaxNormalized_withEmptyList() {
        List<Integer> numbers = Arrays.asList();

        // An empty list should return an empty result
        List<Double> actualScores = ScoreCalculator.maxNormalized(numbers);

        // Verify that the result is an empty list
        assertTrue(actualScores.isEmpty());
    }

    @Test
    public void testMaxNormalized_withSingleNumber() {
        List<Integer> numbers = Arrays.asList(50);

        // Normalization for a single number (50 / 51)
        List<Double> expectedScores = Arrays.asList(50.0 / 51);

        List<Double> actualScores = ScoreCalculator.maxNormalized(numbers);

        // Verify the normalized score
        assertEquals(expectedScores.get(0), actualScores.get(0), 0.0001);
    }
}
