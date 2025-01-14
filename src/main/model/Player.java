package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a baseball player with stats
public class Player implements Writable {

    private String name;
    private String position;
    private int balls;
    private int strikes;
    private int hits;
    private int plateAppearances;
    private int strikeouts;
    private int walks;
    private int homeRuns;
    private double battingAverage;
    
    private static final double MINIMUM_AVERAGE = 0.15;

    private boolean onPlate;

    // REQUIRES: 0 < battingAverage < 1
    // EFFECTS: Constructs a baseball player with name, position and batting average.
    //          Player initially begins with 0 recorded stats.
    public Player(String name, String position, double battingAverage) {
        this.name = name;
        this.position = position;
        this.battingAverage = battingAverage;

        this.hits = 0;
        this.plateAppearances = 0;
        this.strikeouts = 0;
        this.walks = 0;
        this.homeRuns = 0;

        this.balls = 0;
        this.strikes = 0;
        this.onPlate = false;
        EventLog.getInstance().logEvent(new Event("Created player named: " + name + ", position: " 
                            + position + ", batting average: " + battingAverage));

    }

    // MODIFIES: this
    // EFFECTS: sets the batter on plate, incrementing the plateAppearances field, with
    //          a 0-0 count.
    public void batterUp() {
        this.onPlate = true;
        plateAppearances++;
        resetCount();
        EventLog.getInstance().logEvent(new Event(name + " is up to bat!"));
    }

    // REQUIRES: Player be onPlate
    // MODIFIES: this
    // EFFECTS: swings at given pitch. Returns true for a hit, otherwise false.
    //          Hit calculated using batting average and if inside zone.
    //          Calls the updateBattingAverage method after execution.
    //          If 3 strikes are accumulated, player has struckout.
    public boolean swing(Pitch pitch) {

        // Logic here is generating a random number from 0 (inc) to 1 (exc)
        // and if it is within the batting average, generates a hit
        double random = Math.random();
        boolean isHit;

        if (!pitch.isInZone()) {

            // Apply 25% penalty if the pitch is not in the zone
            double adjustedAverage = battingAverage * 0.75;

            isHit = (random <= adjustedAverage);
        } else {
            isHit = (random <= battingAverage);
        }

        if (!isHit) {
            increaseStrikeCount();
        }

        if (strikes >= 3) {
            updateBattingAverage(isHit);
        } else if (isHit) {
            // 25% chance of a home run
            double homeRunOdds = battingAverage / 4;
            if (random <= homeRunOdds) {
                homeRuns++;
            }
            updateBattingAverage(isHit);
        }

        EventLog.getInstance().logEvent(new Event(name + " swings at a pitch!"));

        return (isHit);
        
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + balls;
        result = prime * result + strikes;
        result = prime * result + hits;
        result = prime * result + plateAppearances;
        result = prime * result + strikeouts;
        result = prime * result + walks;
        result = prime * result + homeRuns;
        long temp;
        temp = Double.doubleToLongBits(battingAverage);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (onPlate ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (balls != other.balls)
            return false;
        if (strikes != other.strikes)
            return false;
        if (hits != other.hits)
            return false;
        if (plateAppearances != other.plateAppearances)
            return false;
        if (strikeouts != other.strikeouts)
            return false;
        if (walks != other.walks)
            return false;
        if (homeRuns != other.homeRuns)
            return false;
        if (Double.doubleToLongBits(battingAverage) != Double.doubleToLongBits(other.battingAverage))
            return false;
        if (onPlate != other.onPlate)
            return false;
        return true;
    }

    // REQUIRES: Player be onPlate
    // MODIFIES: this
    // EFFECTS: takes the current pitch, and depending if the pitch was in the strike
    //          zone or not, update the ball/strike count.  Returns true if the pitch
    //          was a ball, false otherwise.
    //          If taking a strike at 2 strikes, player has struckout. 
    public boolean takePitch(Pitch pitch) {   
        if (!pitch.isInZone()) {
            increaseBallCount();
            return true;
        } else {
            increaseStrikeCount();
            
            if (strikes >= 3) {
                updateBattingAverage(false);
                return false;
            }
        }

        EventLog.getInstance().logEvent(new Event(name + " takes a pitch!"));
        return false;
    }

    // MODIFIES: this
    // EFFECTS: updates the player's batting average, increasing if a hit was recorded
    //          or decreasing if no hit was recorded
    public void updateBattingAverage(boolean hit) {
        if (hit) {
            hits++;
        }

        battingAverage = Math.max((double) hits / plateAppearances, MINIMUM_AVERAGE);
    }

    // REQUIRES: Player be onPlate
    // MODIFIES: this
    // EFFECTS: increases the amount of strikes accumulated on the player
    public void increaseStrikeCount() {
        this.strikes++;

        if (strikes == 3) {
            strikeouts++;
            onPlate = false;
        }
    }

    // REQUIRES: Player be onPlate
    // MODIFIES: this
    // EFFECTS: increases the amount of balls accumulated on the player
    public void increaseBallCount() {
        this.balls++;

        if (balls == 4) {
            walks++;
            onPlate = false;
        }
    }

    // REQUIRES: player be onPlate
    // MODIFIES: this
    // EFFECTS: sets the player's ball and strike count back to 0-0 each plate appearance
    public void resetCount() {
        this.balls = 0;
        this.strikes = 0;
    }

    @Override
    public String toString() {
        return "Player [name=" + name + ", position=" + position + ", hits=" + hits + ", plateAppearances="
                + plateAppearances + ", strikeouts=" + strikeouts + ", walks=" + walks + ", homeRuns=" + homeRuns
                + ", battingAverage=" + battingAverage + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getBalls() {
        return balls;
    }

    public int getStrikes() {
        return strikes;
    }

    public int getHits() {
        return hits;
    }

    public int getPlateAppearances() {
        return plateAppearances;
    }

    public int getStrikeouts() {
        return strikeouts;
    }

    public int getWalks() {
        return walks;
    }

    public int getHomeRuns() {
        return homeRuns;
    }

    public void setBattingAverage(double battingAverage) {
        this.battingAverage = battingAverage;
    }

    public double getBattingAverage() {
        return battingAverage;
    }

    public boolean isOnPlate() {
        return onPlate;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setPlateAppearances(int plateAppearances) {
        this.plateAppearances = plateAppearances;
    }

    public void setStrikeouts(int strikeouts) {
        this.strikeouts = strikeouts;
    }

    public void setWalks(int walks) {
        this.walks = walks;
    }

    public void setHomeRuns(int homeRuns) {
        this.homeRuns = homeRuns;
    }

    public void setOnPlate(boolean onPlate) {
        this.onPlate = onPlate;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("position", position);
        jsonObject.put("balls", balls);
        jsonObject.put("strikes", strikes);
        jsonObject.put("plateAppearances", plateAppearances);
        jsonObject.put("strikeouts", strikeouts);
        jsonObject.put("walks", walks);
        jsonObject.put("homeRuns", homeRuns);
        jsonObject.put("battingAverage", battingAverage);
        jsonObject.put("hits", hits);
        jsonObject.put("onPlate", onPlate);

        return jsonObject;
    }

}