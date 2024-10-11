package com.example.geosuggest.suggestion.util;

import java.util.HashMap;
import java.util.Map;

public class FIPSMapper {

    // HashMap to store FIPS code to region mapping
    private static final Map<String, String> fipsToRegionMap = new HashMap<>();

    // Static block to initialize the mapping
    static {
        fipsToRegionMap.put("01", "Alberta");
        fipsToRegionMap.put("02", "British Columbia");
        fipsToRegionMap.put("03", "Manitoba");
        fipsToRegionMap.put("04", "New Brunswick");
        fipsToRegionMap.put("05", "Newfoundland and Labrador");
        fipsToRegionMap.put("07", "Nova Scotia");
        fipsToRegionMap.put("08", "Ontario");
        fipsToRegionMap.put("09", "Prince Edward Island");
        fipsToRegionMap.put("10", "Quebec");
        fipsToRegionMap.put("11", "Saskatchewan");
        fipsToRegionMap.put("12", "Yukon");
        fipsToRegionMap.put("13", "Northwest Territories");
        fipsToRegionMap.put("14", "Nunavut");
    }

    /**
     * Retrieves the region name associated with a given FIPS (Federal Information Processing Standards) code.
     *
     * This method takes a FIPS code as input and returns the corresponding region name.
     * FIPS codes are standardized codes used by the U.S. government to identify geographic areas.
     * The method looks up the code in a predefined mapping and returns the region name
     * associated with it. If the FIPS code does not exist in the mapping, the method returns
     * the passed FIPS code parameter.
     *
     * @param fipsCode The FIPS code for which the corresponding region name is requested.
     * @return The name of the region associated with the provided FIPS code, or the passed fipsCode if the code is not found.
     */
    public static String getRegionByFIPS(String fipsCode) {
        return fipsToRegionMap.getOrDefault(fipsCode, fipsCode);
    }
}
