package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.League;
import model.Team;

// Modelled from JsonReaderTest in the JsonSerializationDemo example.
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNoFileExists() {
        JsonReader reader = new JsonReader("./data/fileNoExist");
        
        try {
            League league = reader.readLeague();
            fail("IOException expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testReaderEmptyLeague() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLeague.json");

        try {
            League league = reader.readLeague();
            assertEquals("MLB", league.getLeagueName());
            assertEquals(50, league.getTotalGames());
            assertEquals(0, league.getNumTeams());
            assertEquals(0, league.getTeams().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderLeagueWithTeams() {
        JsonReader reader = new JsonReader("./data/testReaderLeagueWithTeams.json");

        try {
            League league = reader.readLeague();
            assertEquals("MLB", league.getLeagueName());
            assertEquals(162, league.getTotalGames());
            assertEquals(2, league.getNumTeams());
            assertEquals(2, league.getTeams().size());

            Team dodgers = league.getTeams().get(0);
            Team yankees = league.getTeams().get(1);
            checkTeam("Dodgers", 98, 64, dodgers.getPlayers(), dodgers);
            checkTeam("Yankees", 94, 68, yankees.getPlayers(), yankees);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
