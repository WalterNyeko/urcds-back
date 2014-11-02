package com.sweroad.util;

import com.sweroad.model.Crash;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 11/2/14.
 */
public class CrashHelperTest {
    @Test
    public void testIsUpdatedVersionOf() {
        Crash crash1 = new Crash();
        crash1.setId(1L);
        crash1.setTarNo("TAR-001");
        Crash crash2 = new Crash();
        crash2.setId(1L);
        crash2.setTarNo(crash1.getTarNo() + "-Updated");
        assertTrue(CrashHelper.isUpdatedVersionOf(crash1, crash2));
    }
}
