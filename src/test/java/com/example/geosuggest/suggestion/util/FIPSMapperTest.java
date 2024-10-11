package com.example.geosuggest.suggestion.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FIPSMapperTest {

    @Test
    public void testGetRegionByFIPS_validCode() {
        assertEquals("Alberta", FIPSMapper.getRegionByFIPS("01"));
        assertEquals("British Columbia", FIPSMapper.getRegionByFIPS("02"));
        assertEquals("Manitoba", FIPSMapper.getRegionByFIPS("03"));
        assertEquals("Ontario", FIPSMapper.getRegionByFIPS("08"));
        assertEquals("Quebec", FIPSMapper.getRegionByFIPS("10"));
    }

    @Test
    public void testGetRegionByFIPS_invalidCode() {
        assertEquals("99", FIPSMapper.getRegionByFIPS("99")); // Should return the FIPS code itself
    }

    @Test
    public void testGetRegionByFIPS_nullCode() {
        assertNull(FIPSMapper.getRegionByFIPS(null)); // Should return null if the code is null
    }

    @Test
    public void testGetRegionByFIPS_emptyCode() {
        assertEquals("", FIPSMapper.getRegionByFIPS("")); // Should return the empty string if the code is empty
    }
}
