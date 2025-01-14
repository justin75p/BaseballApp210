package model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPitch {

    private Pitch fastball;
    private Pitch curveball;
    private Pitch slider;

    @BeforeEach
    public void runBefore() {
        fastball = new Pitch("fastball", 95);
        curveball = new Pitch("curveball", 70);
        slider = new Pitch("slider", 85);
    }

    @Test
    public void testConstructor() {
        assertEquals("fastball", fastball.getPitchType());
        assertEquals("curveball", curveball.getPitchType());
        assertEquals("slider", slider.getPitchType());

        assertEquals(95, fastball.getVelocity());
        assertEquals(70, curveball.getVelocity());
        assertEquals(85, slider.getVelocity());

        assertFalse(fastball.isInZone());
        assertFalse(curveball.isInZone());
        assertFalse(slider.isInZone());

    }

    @Test
    public void testRandomizeInZone() {
        int inZoneCount = 0;
        int outOfZoneCount = 0;

        for (int i = 0; i < 100; i++) {
            fastball.throwPitch();
            
            if (fastball.isInZone()) {
                inZoneCount++;
            } else {
                outOfZoneCount++;
            }
        }

        assertTrue(inZoneCount > 0);
        assertTrue(outOfZoneCount > 0);
    }

}
