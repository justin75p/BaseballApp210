package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a league of baseball teams
public class League implements Writable {

    private ArrayList<Team> teams;
    private int totalGames;
    private String leagueName;
    // Players could be on teams, or not on teams
    private ArrayList<Player> playersInLeague;

    // EFFECTS: constructs an empty league with given name and given number of games, and no players.
    public League(String leagueName, int totalGames) {
        teams = new ArrayList<Team>();
        this.leagueName = leagueName;
        this.totalGames = totalGames;
        playersInLeague = new ArrayList<Player>();
        EventLog.getInstance().logEvent(new Event("Created a new league named " 
                                        + leagueName + " with " + totalGames + " games."));
    }

    // MODIFIES: this
    // EFFECTS: adds the given team into the league
    public void addTeam(Team team) {
        teams.add(team);
        EventLog.getInstance().logEvent(new Event("Added team " + team.getName() + " to league " + leagueName));
    }

    // MODIFIES: this
    // EFFECTS: adds specified player to the league
    public void addPlayer(Player player) {
        playersInLeague.add(player);
        EventLog.getInstance().logEvent(new Event("Added player " + player.getName() + " to league " + leagueName));

    }

    public ArrayList<Player> getPlayersInLeague() {
        return playersInLeague;
    }

    // MODIFIES: this
    // EFFECTS: organizes the teams in terms of their record
    public void rankTeams() {

        for (int i = 0; i < teams.size() - 1; i++) {
            int index = i;

            for (int j = i + 1; j < teams.size(); j++) { 
                if (teams.get(j).getWins() > teams.get(index).getWins()) {
                    index = j;
                }
            }

            if (index != i) {
                Team temp = teams.get(i);
                teams.set(i, teams.get(index));
                teams.set(index, temp);
            }
        }
    }

    @Override
    public String toString() {
        return "League [teams=" + teams + ", totalGames=" + totalGames + ", leagueName=" + leagueName + "]";
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public int getNumTeams() {
        return teams.size();
    }

    public int getTotalGames() {
        return totalGames;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("leagueName", leagueName);
        json.put("totalGames", totalGames);
        json.put("playersInLeague", playersInLeagueToJson());
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns teams in this league as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();
        
        for (Team team : teams) {
            jsonArray.put(team.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns players in this league as a JSON array
    private JSONArray playersInLeagueToJson() {
        JSONArray jsonArray = new JSONArray();
        
        for (Player player : playersInLeague) {
            jsonArray.put(player.toJson());
        }

        return jsonArray;
    }
}
