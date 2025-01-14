package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import model.Event;
import model.EventLog;
import model.League;
import model.Pitch;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;


// Represents the graphical user interface for the BaseballApp class
public class BaseballAppGUI {

    private League league;
    private JFrame frame;
    private JPanel titlePanel;
    private JPanel teamListPanel;
    private JPanel menuPanel;
    private JPanel feedbackPanel;
    private JLabel titleLabel;
    private ArrayList<JButton> buttons;
    private JTextArea textArea;
    private JScrollPane teamScrollPane;
    private JScrollPane scrollTextArea;

    private BufferedImage bufferedImage;
    private Image image;
    private ImageIcon imageIcon;

    private Map<Team, JPanel> teamPanels;
    private int teamIndex;

    private static final String FILE_PATH = "./data/leagueGUI.json";
    private static final String IMAGE_PATH = "./data/baseball.png";
    private static Random random;
    private JsonWriter writer;
    private JsonReader reader;  

    
    public BaseballAppGUI() {

        writer = new JsonWriter(FILE_PATH);
        reader = new JsonReader(FILE_PATH);
        random = new Random();
        teamPanels = new HashMap<>();
        teamIndex = 1;
        
        
        // Main frame using a BorderLayout
        frame = new JFrame();
        initializeMainFrame();

        // Title panel
        titlePanel = new JPanel();
        initializeTitlePanel();

        // Team panel
        teamListPanel = new JPanel();
        teamScrollPane = initializeTeamScrollPanel();

        // Menu Panel
        menuPanel = new JPanel();
        buttons = new ArrayList<JButton>();
        initializeMenuPanel();

        // Feedback panel
        feedbackPanel = new JPanel();
        initializeFeedbackPanel();
        
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(teamScrollPane, BorderLayout.CENTER);
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(feedbackPanel, BorderLayout.SOUTH);

        
        frame.setVisible(true);
    }

    // MODIFIES: frame
    // EFFECTS: Initializes the main frame that displays all the JPanels
    private void initializeMainFrame() {
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BaseballAppGUI");
        frame.pack();
        frame.setSize(650, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    // MODIFIES: titlePanel
    // EFFECTS: Initializes header of the program
    private void initializeTitlePanel() {
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        titleLabel = new JLabel("Baseball League Simulator");
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 36));
        titlePanel.add(titleLabel);

        try {
            bufferedImage = ImageIO.read(new File(IMAGE_PATH));
            image = bufferedImage.getScaledInstance(80, 50, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            titleLabel.setIcon(imageIcon);
        } catch (IOException e) {
            System.out.println("Unable to read from: " + IMAGE_PATH);
        }
    }

    // MODIFIES: teamPanel
    // EFFECTS: Creates and returns a new JScrollPane that wraps around teamPanel, making teamPanel scrollable.
    private JScrollPane initializeTeamScrollPanel() {
        // Teams are displayed vertically
        teamListPanel.setLayout(new BoxLayout(teamListPanel, BoxLayout.Y_AXIS));
        teamListPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        // Make the teamPanel scrollable
        teamScrollPane = new JScrollPane(teamListPanel);
        teamScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        teamScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return teamScrollPane;
    }

    // MODIFIES: menuPanel
    // EFFECTS: Initializes the JPanel used to display the menu buttons
    private void initializeMenuPanel() {
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        buttons.add(new JButton("Create League"));
        buttons.add(new JButton("Create Team"));
        buttons.add(new JButton("Create Player"));
        buttons.add(new JButton("Add Player to Team"));
        buttons.add(new JButton("Simulate an at-bat"));
        buttons.add(new JButton("Save League"));
        buttons.add(new JButton("Load League"));
        buttons.add(new JButton("Exit Application"));

        for (JButton button : buttons) {
            button.setAlignmentX(JButton.CENTER_ALIGNMENT);
            menuPanel.add(button);
            menuPanel.add(Box.createVerticalStrut(10));
        }

        buttons.get(0).addActionListener(new CreateLeagueButtonHandler());
        buttons.get(1).addActionListener(new CreateTeamButtonHandler());
        buttons.get(2).addActionListener(new CreatePlayerButtonHandler());
        buttons.get(3).addActionListener(new AddPlayerToTeamButtonHandler());
        buttons.get(4).addActionListener(new SimulateAtBatButtonHandler());
        buttons.get(5).addActionListener(new SaveLeagueButtonHandler());
        buttons.get(6).addActionListener(new LoadLeagueButtonHandler());
        buttons.get(7).addActionListener(new QuitProgramButtonHandler());

    }

    // MODIFIES: commandPanel
    // EFFECTS: Initializes the commandPanel used to show the program's output and user input
    private void initializeFeedbackPanel() {
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));

        textArea = new JTextArea();
        textArea.setBorder(new EmptyBorder(2, 2, 2, 2));
        textArea.setEditable(false);
        textArea.setFont(new Font("Sans-serif", Font.BOLD, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollTextArea = new JScrollPane(textArea);
        scrollTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTextArea.setPreferredSize(new Dimension(0, 275));

        feedbackPanel.add(scrollTextArea);
    }

    // MODIFIES: teamPanel
    // EFFECTS: adds a JPanel containing JLabel to teamPanel to display a team in the league
    private void addTeamToTeamPanel(Team team) {
        JPanel teamInfoPanel = new JPanel();
        teamInfoPanel.setLayout(new BoxLayout(teamInfoPanel, BoxLayout.Y_AXIS));

        JLabel teamName = new JLabel(teamIndex++ + ".) " + team.getName() + " (" + team.getWins() 
                + "-" + team.getLosses() + ")");
        teamName.setHorizontalAlignment(JLabel.LEFT);
        teamName.setFont(new Font("Sans-serif", Font.BOLD, 16));
        teamInfoPanel.add(teamName);

        teamPanels.put(team, teamInfoPanel);
        teamListPanel.add(teamInfoPanel);

        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: teamPanel
    // EFFECTS: adds a description of the player to the teamPanel underneath the player's
    //          respective team
    private void addPlayertoTeamPanel(Player player, Team team) {
        JPanel teamInfoPanel = teamPanels.get(team);
        if (teamInfoPanel == null) {
            textArea.append("There exists no such team!");
            return;
        }
        JLabel playerLabel = new JLabel("<html><body style='width: 350px;'>" + player.toString() + "</body></html>");
        playerLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        playerLabel.setOpaque(true);
        playerLabel.setBackground(Color.LIGHT_GRAY);
        teamInfoPanel.add(playerLabel);

        frame.revalidate();
        frame.repaint();
    }

    
    // EFFECTS: prompts user to select a player from the list of players in the league
    private Player selectPlayerByNumber(String inputDialog) {       
        try {
            String message = "";
            int i = 1;
            for (Player player: league.getPlayersInLeague()) {
                message += (i + ". " + player.getName() + "\n");
                i++;
            }
            String choice = JOptionPane.showInputDialog(inputDialog + "\n" + message);
            if (Integer.parseInt(choice) < 1 || Integer.parseInt(choice) > league.getPlayersInLeague().size()) {
                textArea.append("Invalid choice. Try again.\n");
                return null;
            }
            Player selectedPlayer = league.getPlayersInLeague().get(Integer.parseInt(choice) - 1);
            return selectedPlayer;
        } catch (NumberFormatException n) {
            textArea.append("Invalid input. Try again.\n");
            return null;
        }
    }

    private void refreshAllTeams() {
        teamListPanel.removeAll();
    
        int index = 1;
        for (Team team : league.getTeams()) {
            JPanel teamInfoPanel = teamPanels.get(team);
            if (teamInfoPanel == null) {
                teamInfoPanel = new JPanel();
                teamInfoPanel.setLayout(new BoxLayout(teamInfoPanel, BoxLayout.Y_AXIS));
                teamPanels.put(team, teamInfoPanel);
            }
            teamInfoPanel.removeAll();
    
            JLabel teamName = new JLabel(index + ".) " + team.getName() + " (" + team.getWins() 
                    + "-" + team.getLosses() + ")");
            teamName.setHorizontalAlignment(JLabel.LEFT);
            teamName.setFont(new Font("Sans-serif", Font.BOLD, 16));
            teamInfoPanel.add(teamName);
    
            for (Player player : team.getPlayers()) {
                JLabel playerLabel = new JLabel("<html><body style='width: 350px;'>" + player.toString() 
                        + "</body></html>");
                playerLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
                playerLabel.setOpaque(true);
                playerLabel.setBackground(Color.LIGHT_GRAY);
                teamInfoPanel.add(playerLabel);
            }
    
            teamListPanel.add(teamInfoPanel);
            index++;
        }
    
        frame.revalidate();
        frame.repaint();
    }
    
    
    


    public class CreateLeagueButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (league != null) {
                textArea.append("You already have a league.\n");
                return;
            }

            String leagueName = JOptionPane.showInputDialog("Enter your league name:");
            if (leagueName == null || leagueName.trim().isEmpty()) {
                textArea.append("League name is required!\n");
                return;
            }

            String totalGamesString = JOptionPane.showInputDialog("Enter amount of games in the league:");
            try {
                Integer.parseInt(totalGamesString);
            } catch (NumberFormatException n) {
                textArea.append("Invalid input for number of games.\n");
                return;
            }
            
            league = new League(leagueName, Integer.parseInt(totalGamesString));
            textArea.append("Creating a league with name: " + leagueName 
                            + " and total games: " + totalGamesString + "\n");
        }

    }

    public class CreateTeamButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (league == null) {
                textArea.append("Please create a league before creating a team.\n");
                return;
            }

            String teamName = JOptionPane.showInputDialog("Enter team name:");
            if (teamName == null || teamName.trim().isEmpty()) {
                textArea.append("Team name is required!\n");
                return;
            }

            Team team = new Team(teamName);
            league.addTeam(team);
            textArea.append("Creating a team with name: " + teamName 
                            + " and adding it to " + league.getLeagueName() + "\n");
            addTeamToTeamPanel(team);
        }
        
    }

    public class CreatePlayerButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (league == null) {
                textArea.append("Please create a league before creating a player.\n");
                return;
            }

            String name = JOptionPane.showInputDialog("Enter player name:");
            String position = JOptionPane.showInputDialog("Enter position:");
            if (name == null || name.trim().isEmpty() || position == null || position.trim().isEmpty()) {
                textArea.append("Player name/position is required!\n");
                return;
            }

            String averageString = JOptionPane.showInputDialog("Enter batting average:");
            try {
                Double.parseDouble(averageString);
            } catch (NumberFormatException n) {
                textArea.append("Invalid input for batting average.\n");
                return;
            }
            Player player = new Player(name, position, Double.parseDouble(averageString));
            league.getPlayersInLeague().add(player);
            textArea.append("Creating player named: " + name + ", position: " 
                            + position + ", batting average: " + averageString + "\n");
        }
        
    }

    public class AddPlayerToTeamButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (league == null) {
                textArea.append("Please create a league first.\n");
            } else if (league.getNumTeams() == 0) {
                textArea.append("Please create a team first.\n");
            } else if (league.getPlayersInLeague().isEmpty()) {
                textArea.append("Please create a player first.\n");
            } else {
                Team selectedTeam = selectTeamByNumber();
                if (selectedTeam == null) {
                    return;
                }

                Player selectedPlayer = selectPlayerByNumber("Select a player by number to add to team:");
                if (selectedPlayer == null) {
                    return;
                }

                if (playerOnAnotherTeam(selectedPlayer)) {
                    return;
                }

                selectedTeam.addPlayer(selectedPlayer);
                addPlayertoTeamPanel(selectedPlayer, selectedTeam);
                textArea.append("Added player: " + selectedPlayer.getName() 
                                + " to team: " + selectedTeam.getName() + "\n");
            }
        }

        // EFFECTS: prompts user to select a team by their number in the teams panel
        private Team selectTeamByNumber() {
            try {
                String choice = JOptionPane.showInputDialog("Select a team by their number:\n");
                if (Integer.parseInt(choice) < 1 || Integer.parseInt(choice) > league.getTeams().size()) {
                    textArea.append("Invalid choice. Try again.\n");
                }
                Team selectedTeam = league.getTeams().get(Integer.parseInt(choice) - 1);
                return selectedTeam;
            } catch (NumberFormatException n) {
                textArea.append("Invalid input. Try again.\n");
                return null;
            }
        }

        // EFFECTS: checks if the player is on another team when adding a player
        //          to a team.
        private boolean playerOnAnotherTeam(Player player) {
            ArrayList<Team> teams = league.getTeams();

            for (Team team: teams) {
                if (team.getPlayers().contains(player)) {
                    textArea.append("This player is already on another team.\n");
                    return true;
                }
            }
            return false;
        }


    }

    

    public class SimulateAtBatButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (league == null || league.getTeams() == null || league.getPlayersInLeague() == null) {
                textArea.append("You must first have a league with a team and players!\n");
                return;
            }
            Player selectedPlayer = selectPlayerByNumber("Select a player by number to simulate at-bat:\n");
            Team playersTeam = checkPlayerOnATeam(selectedPlayer);

            if (playersTeam == null) {
                textArea.append("The selected player is not on a team!\n");
                return;
            }
            
            boolean out = false;
            textArea.append("Simulating at-bat!\n");

            selectedPlayer.batterUp();
            while (!out) {
                Pitch pitch = BaseballApp.generateRandomPitch(random);
                pitch.throwPitch();

                String message = "Count: " + selectedPlayer.getBalls() + " Balls, " + selectedPlayer.getStrikes() 
                        + " Strikes.\nThe pitcher throws the ball, you recognize it as a " + pitch.getPitchType() 
                        + " coming in at " + pitch.getVelocity() + " miles per hour.\nSwing at this next pitch?";
                out = swingOrTakeHandler(playersTeam, selectedPlayer, pitch, message);
            }
            refreshAllTeams();
        }

        // EFFECTS: handles input from user, whether to take or swing at a pitch.
        private boolean swingOrTakeHandler(Team playersTeam, Player player, Pitch pitch, String message) {

            int choice = JOptionPane.showConfirmDialog(null, message, "At-bat", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                boolean hit = player.swing(pitch);
                if (hit) {
                    JOptionPane.showMessageDialog(null, "You got a hit!");
                    playersTeam.recordWin();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Swing and a miss!");
                    if (player.getStrikes() == 3) {
                        JOptionPane.showMessageDialog(null, "You struck out!");
                        playersTeam.recordLoss(); 
                    }
                }
            } else if (choice == JOptionPane.NO_OPTION) {
                boolean isBall = player.takePitch(pitch);
                JOptionPane.showMessageDialog(null, isBall ? "Ball!" : "Strike!");
                if (player.getStrikes() == 3) {
                    JOptionPane.showMessageDialog(null, "You struck out!");
                    playersTeam.recordLoss();
                } else if (player.getBalls() == 4) {
                    JOptionPane.showMessageDialog(null, "You were walked!");
                    playersTeam.recordWin();
                }
            }
            return player.getStrikes() == 3 || player.getBalls() == 4;
        }

        private Team checkPlayerOnATeam(Player selectedPlayer) {
            for (Team team : league.getTeams()) {
                for (Player player : team.getPlayers()) {
                    if (player.equals(selectedPlayer)) {
                        return team;
                    }
                }
            }

            return null;
        }
        
    }

    public class SaveLeagueButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (league == null) {
                textArea.append("Please create a league first.\n");
            }

            try {
                writer.open();
                writer.write(league);
                writer.close();
                textArea.append("Saved " + league.getLeagueName() + " to " + FILE_PATH + "\n");
            } catch (IOException io) {
                textArea.append("Unable to write to file: " + FILE_PATH + "\n");
            }
        }
        
    }

    public class LoadLeagueButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                league = reader.readLeague();
                textArea.append("Loaded " + league.getLeagueName() + " from " + FILE_PATH + "\n");

                refreshAllTeams();

            } catch (IOException io) {
                textArea.append("Unable to read from file: " + FILE_PATH + "\n");
            }
        }
        
    }

    public class QuitProgramButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString() + "\n");
            }
            frame.dispose();
        }
        
    }
    

    public static void main(String[] args) {
        new BaseballAppGUI();
    }

}
