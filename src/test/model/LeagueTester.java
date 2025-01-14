package model;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LeagueTester {

    private League league;

    @BeforeEach
    public void runBefore() {
        league = new League("MLB", 10);
    }

    @Test
    public void testConstructor() {
        assertEquals("MLB", league.getLeagueName());
        assertEquals(10, league.getTotalGames());
        assertEquals(0, league.getNumTeams());
    }

    @Test
    public void testAddTeamSingle() {
        Team team = new Team("Bad Batters");
        league.addTeam(team);

        assertEquals(1, league.getNumTeams());
        assertEquals(team, league.getTeams().get(0));
    }

    @Test
    public void testAddTeamMultiple() {
        Team team1 = new Team("Bad Batters");
        Team team2 = new Team("Red Raiders");
        Team team3 = new Team("Great Guys");
        Team team4 = new Team("Fast Foxes");

        league.addTeam(team1);
        league.addTeam(team2);
        league.addTeam(team3);
        league.addTeam(team4);

        assertEquals(4, league.getNumTeams());
        assertEquals(team1, league.getTeams().get(0));
        assertEquals(team2, league.getTeams().get(1));
        assertEquals(team3, league.getTeams().get(2));
        assertEquals(team4, league.getTeams().get(3));
    }

    @Test
    public void testRankTeams() {
        Team team1 = new Team("Bad Batters");
        Team team2 = new Team("Red Raiders");
        Team team3 = new Team("Great Guys");
        Team team4 = new Team("Fast Foxes");

        league.addTeam(team1);
        league.addTeam(team2);
        league.addTeam(team3);
        league.addTeam(team4);

        team1.setWins(5);
        team2.setWins(2);
        team3.setWins(4);
        team4.setWins(8);

        league.rankTeams();

        assertEquals(team4, league.getTeams().get(0));
        assertEquals(team1, league.getTeams().get(1));
        assertEquals(team3, league.getTeams().get(2));
        assertEquals(team2, league.getTeams().get(3));
    }

}
