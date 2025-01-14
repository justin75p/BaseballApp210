package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.League;
import model.Player;
import model.Team;

// Modelled from JsonWriterTest in the JsonSerializationDemo example.
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            League league = new League("Little League", 25);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testWriterEmptyLeague() {
        try {
            League league = new League("Little League", 25);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeague.json");
            writer.open();
            writer.write(league);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLeague.json");
            league = reader.readLeague();
            assertEquals("Little League", league.getLeagueName());
            assertEquals(25, league.getTotalGames());
            assertEquals(0, league.getNumTeams());
            assertEquals(0, league.getTeams().size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test 
    void testWriterLeagueWithTeams() {
        try {
            League league = new League("MLB", 162);
            Team mets = new Team("Mets");
            mets.setWins(89);
            mets.setLosses(73);
            mets.addPlayer(new Player("Lindor", "SS", 0.273));

            Team padres = new Team("Padres");
            padres.setWins(93);
            padres.setLosses(69);
            padres.addPlayer(new Player("Tatis", "RF", .276));

            league.addTeam(padres);
            league.addTeam(mets);

            JsonWriter writer = new JsonWriter("./data/testWriterLeagueWithTeams.json");
            writer.open();
            writer.write(league);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterLeagueWithTeams.json");
            league = reader.readLeague();
            assertEquals("MLB", league.getLeagueName());
            assertEquals(162, league.getTotalGames());
            assertEquals(2, league.getNumTeams());
            assertEquals(2, league.getTeams().size());
            checkTeam("Mets", 89, 73, mets.getPlayers(), mets);
            checkTeam("Padres", 93, 69, padres.getPlayers(), padres);


        } catch (Exception e) {
            fail("IOException should not have been thrown");
        }
    }
}
