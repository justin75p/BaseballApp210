package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.League;
import model.Pitch;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

public class BaseballApp {

    private League league;
    private Scanner scanner;
    private boolean running;
    private static Random random;
    private static final String FILE_PATH = "./data/league.json";
    private JsonWriter writer;
    private JsonReader reader;  

    // EFFECTS: constructs an application to showcase a baseball league simulation.
    public BaseballApp() {
        scanner = new Scanner(System.in);
        running = true;
        random = new Random();
        writer = new JsonWriter(FILE_PATH);
        reader = new JsonReader(FILE_PATH);
    }

    // MODIFIES: this
    // EFFECTS: displays a menu to the user to interact with the BaseballApp.
    @SuppressWarnings("methodlength")
    public void start() {

        System.out.println("Welcome to my Baseball Project!");
        while (running) { 
            System.out.println("\nSelect one of the options below:");
            System.out.println("1. Create a player");
            System.out.println("2. Create a team");
            System.out.println("3. Create your league");
            System.out.println("4. Simulate game");
            System.out.println("5. View Players");
            System.out.println("6. Add Player to Team");
            System.out.println("7. View Teams");
            System.out.println("8. Save your league");
            System.out.println("9. Load in your league");
            System.out.println("10. Exit program");

            int input = scanner.nextInt();

            switch (input) {
                case 1:
                    if (league == null) {
                        System.out.println("\nPlease create a league first.\n");
                    } else {
                        createPlayer();
                    }
                    break;
                case 2:
                    if (league == null) {
                        System.out.println("\nPlease create a league first.\n");
                    } else {
                        createTeam();
                    }
                    break;
                case 3:
                    if (league != null) {
                        System.out.println("You already have a league.");
                    } else {
                        createLeague();
                    }
                    break;

                case 4:
                    if (league == null || league.getNumTeams() == 0) {
                        System.out.println("\nPlease create a league/team first.\n");
                    } else if (league.getPlayersInLeague().size() == 0) {
                        System.out.println("\nPlease create players first.\n");
                    } else {
                        simulateGame();
                    }
                    break;

                case 5:
                    if (league == null || league.getNumTeams() == 0) {
                        System.out.println("\nPlease create a league/team first.\n");
                    } else {
                        viewPlayers();
                    }
                    break;

                case 6:
                    if (league == null || league.getNumTeams() == 0) {
                        System.out.println("\nPlease create a league/team first.\n");
                    } else {
                        addPlayerToTeam();
                    }
                    break;

                case 7:
                    if (league == null || league.getNumTeams() == 0) {
                        System.out.println("\nPlease create a league/team first.\n");
                    } else {
                        viewTeams();
                    }
                    break;

                case 8:
                    if (league == null) {
                        System.out.println("\nPlease create a league first.\n");
                    } else {
                        saveLeague();
                    }
                    break;
                case 9:
                    loadLeague();
                    break;
                case 10:
                    System.out.println("Exiting program..");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input.  Try again.");
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: creates a defined player and adds it to the league.
    private void createPlayer() {
        scanner.nextLine();
        System.out.println("Enter player name: ");
        String name = scanner.nextLine();
        System.out.println("Enter position: ");
        String position = scanner.next();
        System.out.println("Enter batting average: ");
        double average = scanner.nextDouble();
        
        System.out.println("\nCreating player named: " + name + ", position: " + position + ", batting average: " 
                            + average + "\n");
        Player player = new Player(name, position, average);
        league.getPlayersInLeague().add(player);

    }

    // MODIFIES: this
    // EFFECTS: creates a league that carries all players and teams.
    private void createLeague() {
        scanner.nextLine();
        System.out.println("Enter league name: ");
        String name = scanner.next();
        System.out.println("Enter total games: ");
        int games = scanner.nextInt();
        System.out.println("\nCreating a league with name " + name + " and " + games + " total games\n");
        league = new League(name, games);
    }

    // MODIFIES: this
    // EFFECTS: creates a team which is then added to the league.
    private void createTeam() {
        scanner.nextLine();
        System.out.println("Enter team name: ");
        String name = scanner.nextLine();
        System.out.println("\nCreating a team with name: " + name + " and adding it to " 
                            + league.getLeagueName() + "\n");
        Team team = new Team(name);
        league.addTeam(team);
    }

    // MODIFIES: this
    // EFFECTS: simulates a plate appearance for the selected player.
    private void simulateGame() {
        Player player = selectPlayer();
        scanner.nextLine();
        System.out.println("Simulating game!");

        boolean out = false;
        player.batterUp();

        while (!out) {
            Pitch pitch = generateRandomPitch(random);
            pitch.throwPitch();

            System.out.println(pitch.toString());

            out = swingOrTakeHandler(player, pitch);
        }  
    }
    

    // EFFECTS: generates a random pitch to be thrown to the player.
    public static Pitch generateRandomPitch(Random random) {
        // Currently 3 pitches available
        int num = random.nextInt(3) + 1;
        Pitch pitch = null;

        switch (num) {
            case 1:
                pitch = new Pitch("fastball", 95);
                break;
                
            case 2:
                pitch = new Pitch("curveball", 70);
                break;
                
            case 3: 
                pitch = new Pitch("slider", 85);
                break;

        }

        return pitch;
    }

    // EFFECTS: selects the player from all the players in the league
    private Player selectPlayer() {
        System.out.println("Select a player by their number:");

        ArrayList<Player> allPlayers = new ArrayList<Player>();
        for (Player player : league.getPlayersInLeague()) {
            allPlayers.add(player);
        }

        for (int i = 0; i < allPlayers.size(); i++) {
            System.out.println((i + 1) + " " +  allPlayers.get(i).getName());
        }

        int choice = scanner.nextInt();
        if (choice > 0 && choice <= allPlayers.size()) {
            return allPlayers.get(choice - 1);
        } else {
            System.out.println("Invalid input. Try again.");
            return selectPlayer();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a previously created player to a team.
    private void addPlayerToTeam() {
        System.out.println("Select a team by their number:");
        ArrayList<Team> teams = league.getTeams();

        for (int i = 0; i < teams.size(); i++) {
            System.out.println((i + 1) + " " +  teams.get(i).getName());
        }

        int choice = scanner.nextInt();
        if (choice < 1 || choice > teams.size()) {
            System.out.println("Invalid choice. Try again.");
            return;
        }

        Team selectedTeam = teams.get(choice - 1);

        System.out.println("Select a player to add to " + selectedTeam.getName());
        viewPlayers();

        int playerChoice = scanner.nextInt();
        if (playerChoice < 1 || playerChoice > league.getPlayersInLeague().size()) {
            System.out.println("Invalid choice. Try again.");
            return;
        }

        Player selectedPlayer = league.getPlayersInLeague().get(playerChoice - 1);

        if (playerOnAnotherTeam(selectedPlayer)) {
            return;
        }
        
        selectedTeam.addPlayer(selectedPlayer);
    }

    // EFFECTS: shows a list of all the players in the league.
    private void viewPlayers() {
        System.out.print("List of Players: \n");
        int i = 1;
        for (Player player: league.getPlayersInLeague()) {
            System.out.println(i + ". " + player.toString());
            i++;
        }
    }

    // EFFECTS: handles input from user, whether to take or swing at a pitch.
    private boolean swingOrTakeHandler(Player player, Pitch pitch) {

        System.out.println("Swing at this next pitch? y/n\n");
        String choice = scanner.next();
        
        if (choice.equalsIgnoreCase("y")) {
            boolean hit = player.swing(pitch);
            if (hit) {
                System.out.println("You got a hit!\n");
                return true;
            } else {
                System.out.println("Swing and a miss!\n");
            }
        } else if (choice.equalsIgnoreCase("n")) {
            boolean isBall = player.takePitch(pitch);
            System.out.println(isBall ? "Ball!\n" : "Strike!\n");
        }
        if (player.getStrikes() == 3) {
            System.out.println("You struck out!\n");
            return true;
        } else if (player.getBalls() == 4) {
            System.out.println("You were walked!\n");
            return true;
        }
        return false;
    }

    // EFFECTS: checks if the player is on another team when adding a player
    //          to a team.
    private boolean playerOnAnotherTeam(Player player) {
        ArrayList<Team> teams = league.getTeams();

        for (Team team: teams) {
            if (team.getPlayers().contains(player)) {
                System.out.println("This player is already on another team.");
                return true;
            }
        }
        return false;
    }

    // EFFECTS: shows a list of the teams in the league, in order of record.
    private void viewTeams() {

        league.rankTeams();

        ArrayList<Team> teams = league.getTeams();

        System.out.println("List of Teams: \n");

        int i = 1;
        for (Team team: teams) {
            System.out.println(i + ". " + team.toString());
            i++;
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the league for future use.
    private void saveLeague() {
        try {
            writer.open();
            writer.write(league);
            writer.close();
            System.out.println("Saved " + league.getLeagueName() + " to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + FILE_PATH);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads in a previously saved league.
    private void loadLeague() {
        try {
            league = reader.readLeague();
            System.out.println("Loaded " + league.getLeagueName() + " from " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + FILE_PATH);
        }
    }
}
