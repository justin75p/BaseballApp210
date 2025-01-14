package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a team of baseball players
public class Team implements Writable {

    private ArrayList<Player> players;
    private String name;
    private int wins;
    private int losses;

    // EFFECTS: constructs an empty baseball team with given name, and a record of
    // 0-0.
    public Team(String name) {
        players = new ArrayList<Player>();
        this.name = name;
        this.wins = 0;
        this.losses = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds specified player to the team
    public void addPlayer(Player player) {
        players.add(player);
        EventLog.getInstance().logEvent(new Event("Added player " + player.getName() + " to team " + name));
    }
    
    // MODIFIES: this
    // EFFECTS: adds a win to this team's record
    public void recordWin() {
        wins++;
        EventLog.getInstance().logEvent(new Event(name + " wins a game"));
    }

    // MODIFIES: this
    // EFFECTS: adds a loss to this team's record
    public void recordLoss() {
        losses++;
        EventLog.getInstance().logEvent(new Event(name + " loses a game"));
    }

    public String getRecord() {
        return "wins: " + wins + " losses: " + losses;
    }

    
    @Override
    public String toString() {
        String string = name + ": " + getRecord() + "\nPlayers: \n";

        for (Player player : players) {
            string += player.getName() + "\n";
        }

        return string;
    }

    public void setWins(int wins) { 
        this.wins = wins;
    }

    public int getWins() {
        return this.wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getLosses() {
        return this.losses;
    }

    public int getNumPlayers() {
        return players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("wins", wins);
        json.put("losses", losses);
        json.put("players", playersToJson());

        return json;
    }
    
    // EFFECTS: returns players in this team as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player player : players) {
            jsonArray.put(player.toJson());
        }

        return jsonArray;
    }
    
}
