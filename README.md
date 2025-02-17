# My Personal Project

## What will the application do?
For my personal project, I will be designing a 2D baseball batting game application.  In this application,
I plan to be able to closely mimic the gameplay mechanics such as in real-life baseball in Java, either in a graphical or console-
based format.  Users will have the option to either create their own baseball player/team, or to simply play a game for fun.  
If the former option is chosen, the user is able to simulate a season where they can improve their statistics such as batting average.
Users will enter in their player attributes, and that player will be added to a team, and that team will be added to a league.  
In the console-based version, the game will be more randomized and unpredictable, whereas in the graphical version, users can enter 
timing inputs to control the batter.  Stats of the baseball game will be shown to the user, such as balls, strikes, outs, the current
inning, exit velocity/angle/distance of hits, etc.  

## Who will use it, why this project interests me?
Intended users for this application would obviously include those who are interested in baseball, gamers, and other
students or aspiring game developers.  I hope to provide an enjoyable experience to all ranges of users with this application.
This project interests me because it combines two of my interests, baseball and gaming into one, while also providing me with a
challenge to develop an application for it.


Notable stats to be shown in this application:
- Balls
- Strikes
- Pitch Type
- Team Wins
- Team Records
- Player Stats

## User Stories
- As a user, I want to be able to create my own player with custom attributes.  (supported; each player has name, position, and average)
- As a user, I want to be able to create my own team, and add an amount of players to the team. (supported; ability to add team, add players to team)
- As a user, I want to be able to add my team to a league, and customize the amount of games in a season. (supported; adding teams to league, user-defined amount of games)
- As a user, I want to be able to monitor my player's stats, such as BA, HRs, etc. (supported; players stats is shown)
- As a user, I want to be able to view my team's stats and other teams in my league.  **wins, etc will be tracked in graphical version (supported; GUI shows the info of each team and their players).
- As a user, I want to be able to see my team's ranking during the season.  **wins, etc will be tracked in graphical version. (supported; team records are shown)
- As a user, I want to be able to play a console-based version of baseball, where it is mainly luck-based.  (supported; console-based app allows for a luck-based at bat.)
- As a user, I want to be able to face different types of pitches for a unique experience. (supported; console-based app able to throw different pitches)
- As a user, I want to be able to receive feedback from the game, such as pitch type/speed, exit velocity,
distance, etc. (supported)
- As a user, I want to be able to have the option to save my league, containing all the players, teams and stats to file.  (supported; GUI and console-based app able to save an entire league)
- As a user, I want to be able to have the option to load in previously saved leagues and resume from where they left off.  (supported; GUI and console-based app able to load an entire league)

# Instructions for End User

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by pressing the "Add Player To Team" button after creating a player.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by pressing the "Simulate an at-bat" button.
- You can locate my visual component by the top left corner of the application.
- You can save the state of my application by pressing the "Save League" button.
- You can reload the state of my application by pressing the "Load League" button.

# Phase 4: Task 2
Wed Nov 27 13:34:56 PST 2024
Created a new league named MLB with 162 games.

Wed Nov 27 13:35:12 PST 2024
Added team lakers to league MLB

Wed Nov 27 13:35:21 PST 2024
Created player named: lebron, position: CF, batting average: 0.5

Wed Nov 27 13:35:24 PST 2024
Added player lebron to team lakers

Wed Nov 27 13:35:31 PST 2024
Added team warriors to league MLB

Wed Nov 27 13:35:42 PST 2024
Created player named: curry, position: ss, batting average: 0.35

Wed Nov 27 13:35:45 PST 2024
Added player curry to team warriors

Wed Nov 27 13:35:47 PST 2024
lebron is up to bat!

Wed Nov 27 13:35:49 PST 2024
lebron swings at a pitch!

Wed Nov 27 13:35:50 PST 2024
lebron takes a pitch!

Wed Nov 27 13:35:52 PST 2024
lebron swings at a pitch!

Wed Nov 27 13:35:53 PST 2024
lakers loses a game

Wed Nov 27 13:35:55 PST 2024
curry is up to bat!

Wed Nov 27 13:35:56 PST 2024
curry swings at a pitch!

Wed Nov 27 13:35:57 PST 2024
warriors wins a game

# Phase 4: Task 3
If I had more time to work on this project, one of the ways I would refactor my code to improve its design would be to break up my BaseballAppGUI class into smaller, more focused classes. Currently, the GUI contains nearly 600 lines of code and also contains several event handlers, and helper methods making it a very long page of code which could be difficult to maintain and improve upon, and also clouds the overall functionality of the GUI class. By seperating for example, the event handlers into different classes, the GUI class would become easier to maintain and to improve, as it becomes less prone to error due to less responsibilities.