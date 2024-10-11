package com.example.geosuggest.suggestion.util;


import static java.lang.Math.*;

public final class GeoCalculator {
    private GeoCalculator() {}

    /**
     * Calculates the great-circle distance between two points on the Earth's surface
     * specified by their longitude and latitude using the Haversine formula.
     *
     * This method computes the distance in kilometers between two geographic points
     * identified by their longitude and latitude coordinates. The Haversine formula
     * accounts for the curvature of the Earth, providing an accurate distance measurement
     * for relatively short distances. This method assumes the Earth is a perfect sphere,
     * which introduces a small error for larger distances but is generally acceptable
     * for most applications.
     *
     * Implementation is based on <a href="https://stackoverflow.com/a/4913653">Link</a>
     *
     * @param lon1 The longitude of the first point in decimal degrees.
     * @param lat1 The latitude of the first point in decimal degrees.
     * @param lon2 The longitude of the second point in decimal degrees.
     * @param lat2 The latitude of the second point in decimal degrees.
     * @return The distance between the two points in kilometers.
     */
    public static double haversineDistance(double lon1, double lat1, double lon2, double lat2) {
        // Radius of Earth in kilometers. For miles, use 3956
        final double R = 6371;

        // Convert decimal degrees to radians
        lon1 = toRadians(lon1);
        lat1 = toRadians(lat1);
        lon2 = toRadians(lon2);
        lat2 = toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = sin(dlat / 2) * sin(dlat / 2) +
                cos(lat1) * cos(lat2) * sin(dlon / 2) * sin(dlon / 2);
        double c = 2 * asin(sqrt(a));

        // Calculate the distance
        return R * c;
    }
}