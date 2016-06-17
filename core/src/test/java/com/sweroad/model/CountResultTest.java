package com.sweroad.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 6/17/16.
 */
public class CountResultTest {

    private CountResult.CountResultBuilder countResultBuilder;

    @Test
    public void testThatIncrementCountsCorrectlyIncrementsCrashCount() {
        countResultBuilder = new CountResult.CountResultBuilder();
        countResultBuilder.incrementCrashCount(1);
        assertEquals(1, countResultBuilder.build().getCrashCount());
    }

    @Test
    public void testThatIncrementCountsCorrectlyIncrementsMultipleCrashCount() {
        countResultBuilder = new CountResult.CountResultBuilder();
        countResultBuilder.incrementCrashCount(3);
        assertEquals(3, countResultBuilder.build().getCrashCount());
    }

    @Test
    public void testThatIncrementCountsCorrectlyIncrementsVehicleCount() {
        countResultBuilder = new CountResult.CountResultBuilder();
        countResultBuilder.incrementVehicleCount(3);
        assertEquals(3, countResultBuilder.build().getVehicleCount());
    }

    @Test
    public void testThatIncrementCountsCorrectlyIncrementsCasualtyCount() {
        countResultBuilder = new CountResult.CountResultBuilder();
        countResultBuilder.incrementCasualtyCount(8);
        assertEquals(8, countResultBuilder.build().getCasualtyCount());
    }
}
