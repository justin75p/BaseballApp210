package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

public class TestTeam {

    private Team team;

    @BeforeEach
    public void runBefore() {
        team = new Team("Justin's Team");
    }

    @Test
    public void testConstructor() {
        assertEquals("Justin's Team", team.getName());
        assertEquals(0, team.getNumPlayers());
        assertEquals(0, team.getWins());
        assertEquals(0, team.getLosses());
    }

    @Test
    public void testAddPlayerSingle() {
        Player justin = new Player("Justin", "infielder", 0.250);

        team.addPlayer(justin);

        assertEquals(1, team.getNumPlayers());
        assertEquals(justin, team.getPlayers().get(0));
    }

    @Test
    public void testAddPlayerMultiple() {
        Player justin = new Player("Justin", "infielder", 0.250);
        Player jordan = new Player("Jordan", "DH", 0.200);
        Player paul = new Player("Paul", "catcher", 0.400);
        Player freddie = new Player("Freddie", "outfielder", 0.300);

        team.addPlayer(justin);
        team.addPlayer(jordan);
        team.addPlayer(paul);
        team.addPlayer(freddie);


        assertEquals(4, team.getNumPlayers());
        assertEquals(justin, team.getPlayers().get(0));
        assertEquals(jordan, team.getPlayers().get(1));
        assertEquals(paul, team.getPlayers().get(2));
        assertEquals(freddie, team.getPlayers().get(3));
    }

    @Test
    public void testRecordWinSingle() {
        assertEquals(0, team.getWins());

        team.recordWin();

        assertEquals(1, team.getWins());
    }

    @Test
    public void testRecordLossSingle() {
        assertEquals(0, team.getLosses());

        team.recordLoss();

        assertEquals(1, team.getLosses());
    }

    
    @Test
    public void testRecordWinMultiple() {
        assertEquals(0, team.getWins());

        team.recordWin();
        assertEquals(1, team.getWins());

        team.recordWin();
        assertEquals(2, team.getWins());
        
        team.recordWin();
        assertEquals(3, team.getWins());

        team.recordWin();
        assertEquals(4, team.getWins());
    }

    @Test
    public void testRecordLossMultiple() {
        assertEquals(0, team.getLosses());

        team.recordLoss();
        assertEquals(1, team.getLosses());

        team.recordLoss();
        assertEquals(2, team.getLosses());
        
        team.recordLoss();
        assertEquals(3, team.getLosses());

        team.recordLoss();
        assertEquals(4, team.getLosses());
    }


}
