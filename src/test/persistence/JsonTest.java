package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import model.Player;
import model.Team;

// Modelled from JsonTest in the JsonSerializationDemo example
public class JsonTest {

    protected void checkPlayer(String name, String position, double battingAverage, int hits,
            int plateAppearances, int strikeOuts, int walks, int homeRuns, int balls, int strikes,
            boolean onPlate, Player player) {

        assertEquals(name, player.getName());
        assertEquals(position, player.getPosition());
        assertEquals(battingAverage, player.getBattingAverage());

        
        assertEquals(hits, player.getHits());
        assertEquals(plateAppearances, player.getPlateAppearances());
        assertEquals(strikeOuts, player.getStrikeouts());
        assertEquals(walks, player.getWalks());
        assertEquals(homeRuns, player.getHomeRuns());

        assertEquals(balls, player.getBalls());
        assertEquals(strikes, player.getStrikes());
        assertFalse(onPlate);
    }

    protected void checkTeam(String name, int wins, int losses, ArrayList<Player> players, Team team) {

        assertEquals(name, team.getName());
        assertEquals(wins, team.getWins());
        assertEquals(losses, team.getLosses());

        ArrayList<Player> expectedPlayers = team.getPlayers();
        assertEquals(expectedPlayers.size(), players.size());

        for (int i = 0; i < expectedPlayers.size(); i++) {
            Player expected = expectedPlayers.get(i);
            Player actual = players.get(i);

            checkPlayer(expected.getName(), expected.getPosition(), expected.getBattingAverage(),
                    expected.getHits(), expected.getPlateAppearances(),expected.getStrikeouts(), expected.getWalks(),
                    expected.getHomeRuns(), expected.getBalls(), expected.getStrikes(), expected.isOnPlate(), actual);

        }

    }
}
