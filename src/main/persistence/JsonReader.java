package persistence;

import model.League;
import model.Player;
import model.Team;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Modelled from JsonReader in the JsonSerializationDemo example.
// Represents a reader that reads league from JSON data in source file.
public class JsonReader {

    private String fileSource;

    // EFFECTS: constructs a reader to read data from source file.
    public JsonReader(String fileSource) {
        this.fileSource = fileSource;
    }

    // EFFECTS: reads the league from the file and returns it
    // Throws IOException if an error occurs during reading;
    public League readLeague() throws IOException {
        String jsonData = readFile(fileSource);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
    }

    // EFFECTS: reads the file and returns it as a String
    private String readFile(String fileSource) throws IOException {
        StringBuilder builder = new StringBuilder();
        
        try (Stream<String> stream = Files.lines(Paths.get(fileSource), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }

        return builder.toString();
    }

    // EFFECTS: parses league from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        String leagueName = jsonObject.getString("leagueName");
        int totalGames = jsonObject.getInt("totalGames");

        League league = new League(leagueName, totalGames);
        addTeams(league, jsonObject);
        addPlayersInLeague(league, jsonObject);
        return league;
    }

    // MODIFIES: league
    // EFFECTS: parses teams from JSON object and adds them to league
    private void addTeams(League league, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json: jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(league, nextTeam);
        }
    }

    // MODIFIES: team
    // EFFECTS: parses team from JSON object and adds them to league
    private void addTeam(League league, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int wins = jsonObject.getInt("wins");
        int losses = jsonObject.getInt("losses");
        JSONArray players = jsonObject.getJSONArray("players");
        
        Team team = new Team(name);
        team.setWins(wins);
        team.setLosses(losses);
        league.addTeam(team);

        for (int i = 0; i < players.length(); i++) {
            JSONObject player = players.getJSONObject(i);
            
            addPlayer(team, player);
        }

    }

    // MODIFIES: team
    // EFFECTS: parses player from JSON objet and adds them to team
    private void addPlayer(Team team, JSONObject player) {
        String playerName = player.getString("name");
        String position = player.getString("position");
        double battingAverage = player.getDouble("battingAverage");
        int hits = player.getInt("hits");
        int plateAppearances = player.getInt("plateAppearances");
        int strikeouts = player.getInt("strikeouts");
        int walks = player.getInt("walks");
        int homeRuns = player.getInt("homeRuns");
        int balls = player.getInt("balls");
        int strikes = player.getInt("strikes");
        boolean onPlate = player.getBoolean("onPlate");

        Player addedPlayer = new Player(playerName, position, battingAverage);
        addedPlayer.setHits(hits);
        addedPlayer.setPlateAppearances(plateAppearances);
        addedPlayer.setStrikeouts(strikeouts);
        addedPlayer.setWalks(walks);
        addedPlayer.setHomeRuns(homeRuns);
        addedPlayer.setBalls(balls);
        addedPlayer.setStrikes(strikes);
        addedPlayer.setOnPlate(onPlate);

        team.addPlayer(addedPlayer);
    }

    // MODIFIES: league
    // EFFECTS: parses players from JSON object and adds them to league
    private void addPlayersInLeague(League league, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("playersInLeague");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayerToLeague(league, nextPlayer);
        }
    }

    // MODIFIES: league
    // EFFECTS: parses player from JSON objet and adds them to team
    private void addPlayerToLeague(League league, JSONObject player) {
        String playerName = player.getString("name");
        String position = player.getString("position");
        double battingAverage = player.getDouble("battingAverage");
        int hits = player.getInt("hits");
        int plateAppearances = player.getInt("plateAppearances");
        int strikeouts = player.getInt("strikeouts");
        int walks = player.getInt("walks");
        int homeRuns = player.getInt("homeRuns");
        int balls = player.getInt("balls");
        int strikes = player.getInt("strikes");
        boolean onPlate = player.getBoolean("onPlate");

        Player addedPlayer = new Player(playerName, position, battingAverage);
        addedPlayer.setHits(hits);
        addedPlayer.setPlateAppearances(plateAppearances);
        addedPlayer.setStrikeouts(strikeouts);
        addedPlayer.setWalks(walks);
        addedPlayer.setHomeRuns(homeRuns);
        addedPlayer.setBalls(balls);
        addedPlayer.setStrikes(strikes);
        addedPlayer.setOnPlate(onPlate);

        league.addPlayer(addedPlayer);
    }


}
