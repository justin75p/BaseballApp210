package model;

import java.util.Random;

// Represents a pitch thrown to a player
public class Pitch {

    private boolean inZone;
    private int velocity;
    private String pitchType;
    private Random random = new Random();

    // EFFECTS: constructs a pitch, with a given velocity and pitch type.
    public Pitch(String pitchType, int velocity) {
        this.pitchType = pitchType;
        this.velocity = velocity;
        inZone = false;
    }
    
    // EFFECTS: randomizes if this pitch is in the strikezone or not.
    public void throwPitch() {
        inZone = random.nextBoolean();
    }

    public boolean isInZone() {
        return inZone;
    }

    public void setInZone(boolean inZone) {
        this.inZone = inZone;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public String getPitchType() {
        return pitchType;
    }

    public String toString() {
        return "Throwing " + pitchType + " at " + velocity + "MPH," + " in zone? " + inZone; 
    }

}
