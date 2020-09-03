import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {

    public static void main(String[] args) {
        new Pig().program();
    }

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();

    void program() {
        //test();                 // <-------------- Uncomment to run tests!

        final int winPts = 20;    // Points to win (decrease if testing)
        Player[] players;         // The players (array of Player objects)
        Player current;            // Current player for round (must use)
        boolean aborted = false;   // Game aborted by player?
        boolean hasWon = false;
        players = getPlayers();  // ... this (method to read in all players)
        welcomeMsg(winPts);
        current = getRandomPlayer(players); // TODO Set random player to start

        // Main game loop
        while ( !aborted && !hasWon) {
            String choice = getPlayerChoice(current);
            switch(choice) {
                default:
                    out.println("Invalid choice commands are: r = roll , n = next, q = quit");
                    break;
                case "r":
                    int diceNum = rollDice(current);
                    if(diceNum == 1){
                        current.roundPts = 0;
                        current = next(players, current);
                    }
                    else {
                        current.roundPts += diceNum;
                    }
                    roundMsg(diceNum, current);
                    break;
                case "n":
                    current.totalPts += current.roundPts;
                    current.roundPts = 0;
                    if (checkWin(current, winPts))
                    {
                        hasWon = true;
                        break;
                    }
                    current = next(players, current);

                    break;
                case "q":
                    aborted = true;
                    break;
            }
            statusMsg(players);
        }


        // TODO Game logic, using small step, functional decomposition
        gameOverMsg(current, aborted);
    }

    // ---- Game logic methods --------------

    Player next(Player[] players, Player current) {
            int currentPlayerIndex = Arrays.asList(players).indexOf(current);
            return players[(currentPlayerIndex + 1) % players.length];
    }

    boolean checkWin(Player current, int winPts){
        return current.totalPts >= winPts;
    }

    int rollDice(Player player) {
        return rand.nextInt(6) + 1;
    }

    Player getRandomPlayer(Player[] players)
    {

        return players[rand.nextInt(players.length - 1)];
    }
    // TODO
    // ---- IO methods ------------------
    Player[] getPlayers() {
        out.println("Enter number of players");
        int numPlayers = sc.nextInt();
        Player[] players = new Player[numPlayers];

        for (int i=0; i < players.length; i++)
        {
            out.println("Enter a name > ");
            String name = sc.next();
            Player p = new Player();
            p.name = name;
            players[i] = p;
        }
        return players;
    }
    void welcomeMsg(int winPoints) {
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }

    void statusMsg(Player[] players) {
        out.print("Points: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " = " + players[i].totalPts + " ");
        }
        out.println();
    }

    void roundMsg(int result, Player current) {
        if (result > 1) {
            out.println("Got " + result + " running total are " + current.roundPts);
        } else {
            out.println("Got 1 lost it all!");
        }
    }

    void gameOverMsg(Player player, boolean aborted) {
        if (aborted) {
            out.println("Aborted");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts + player.roundPts) + " points");
        }
    }

    String getPlayerChoice(Player player) {
        out.print("Player is " + player.name + " > ");
        return sc.nextLine();
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects
    class Player {
        String name;     // Default null
        int totalPts = 0;    // Total points for all rounds, default 0
        int roundPts = 0;    // Points for a single round, default 0
    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data
        // An array of (no name) Players (probably don't need any name to test)
        Player[] players = {new Player(), new Player(), new Player()};

        // TODO Use for testing of logcial methods (i.e. non-IO methods)

        exit(0);   // End program
    }
}
