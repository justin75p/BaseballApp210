package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayer {
    
    private Player player;
    private Pitch pitch;

    @BeforeEach
    void runBefore() {
        player = new Player("Justin", "outfielder", 0.300);
        pitch = new Pitch("fastball", 95);
    }

    @Test
    void testConstructor() {
        assertEquals("Justin", player.getName());
        assertEquals("outfielder", player.getPosition());
        assertEquals(0.300, player.getBattingAverage());

        assertEquals(0, player.getHits());
        assertEquals(0, player.getPlateAppearances());
        assertEquals(0, player.getStrikeouts());
        assertEquals(0, player.getWalks());
        assertEquals(0, player.getHomeRuns());

        assertEquals(0, player.getBalls());
        assertEquals(0, player.getStrikes());
        assertFalse(player.isOnPlate());
    }

    @Test
    void testBatterUp() {
        player.batterUp();
        assertTrue(player.isOnPlate());
        assertEquals(1, player.getPlateAppearances());
    }

    @Test
    void testSwing() {
        assertFalse(player.swing(pitch));

        player.batterUp();
        player.setBattingAverage(1.0);
        
        assertTrue(player.swing(pitch));
        assertEquals(1, player.getHits());

        player.setBattingAverage(0.0);
        assertFalse(player.swing(pitch));
        assertEquals(1, player.getHits());

        
    }

    @Test
    void testTakePitch() {

        Pitch ball = new Pitch("curveball", 75);
        ball.setInZone(false);
        Pitch strike = new Pitch("fastball", 90);
        strike.setInZone(true);

        player.batterUp();

        assertTrue(player.takePitch(ball));
        assertEquals(1, player.getBalls());
        assertEquals(0, player.getStrikes());

        assertFalse(player.takePitch(strike));
        assertEquals(1, player.getBalls());
        assertEquals(1, player.getStrikes());
    }

    @Test
    void testUpdateBattingAverage() {

        player.batterUp();
        player.updateBattingAverage(true);
        assertEquals(1.0, player.getBattingAverage());

        player.batterUp();
        player.updateBattingAverage(false);
        assertEquals(0.5, player.getBattingAverage());

    }

    @Test
    void testStruckout() {
        assertEquals(0, player.getStrikeouts());

        player.batterUp();
        assertEquals(0, player.getBalls());
        assertEquals(0, player.getStrikes());

        player.increaseStrikeCount();
        player.increaseStrikeCount();
        player.increaseStrikeCount();

        assertEquals(0, player.getBalls());
        assertEquals(3, player.getStrikes());
        assertFalse(player.isOnPlate());

        assertEquals(1, player.getStrikeouts());
    }

    @Test
    void testBatterWalked() {
        assertEquals(0, player.getWalks());

        player.batterUp();
        assertEquals(0, player.getBalls());
        assertEquals(0, player.getStrikes());

        player.increaseBallCount();
        player.increaseBallCount();
        player.increaseBallCount();
        player.increaseBallCount();

        assertEquals(4, player.getBalls());
        assertEquals(0, player.getStrikes());
        assertFalse(player.isOnPlate());

        assertEquals(1, player.getWalks());
    }

    @Test
    void testResetCount() {
        // Plate appearance
        assertEquals(0, player.getStrikeouts());

        player.batterUp();
        assertEquals(0, player.getBalls());
        assertEquals(0, player.getStrikes());

        player.increaseStrikeCount();
        player.increaseStrikeCount();
        player.increaseStrikeCount();

        // Player gets struckout
        assertEquals(0, player.getBalls());
        assertEquals(3, player.getStrikes());

        assertEquals(1, player.getStrikeouts());
        assertFalse(player.isOnPlate());

        player.resetCount();
        player.batterUp();

        assertEquals(0, player.getBalls());
        assertEquals(0, player.getStrikes());
    }
}
