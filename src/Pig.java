import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

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
        boolean hasWon = false;    // A player has won?

        players = getPlayers();

        welcomeMsg(winPts);
        current = getRandomPlayer(players);

        while (!aborted && !hasWon) {
            String choice = getPlayerChoice(current);

            switch (choice) {
                default:
                    out.println("Invalid choice!");
                    commandsMsg();
                    break;
                case "r":
                    int diceNum = rollDice();
                    if (diceNum == 1) {
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

                    if (checkWin(players, winPts)) {
                        hasWon = true;
                        break;
                    }

                    current = next(players, current);
                    statusMsg(players);
                    break;

                case "q":
                    aborted = true;
                    break;
            }
        }

        gameOverMsg(current, aborted);
    }

    // ---- Game logic methods --------------

    boolean checkWin(Player[] players, int winPts) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].totalPts >= winPts) {
                return true;
            }
        }
        return false;
    }

    int rollDice() {
        return rand.nextInt(6) + 1;
    }

    Player getRandomPlayer(Player[] players) {
        return players[rand.nextInt(players.length)];
    }

    Player next(Player[] players, Player current) {
        int currentPlayerIndex = Arrays.asList(players).indexOf(current);
        return players[(currentPlayerIndex + 1) % players.length];
    }

    // ---- IO methods ------------------

    void welcomeMsg(int winPoints) {
        out.println("\nWelcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        commandsMsg();
        out.println();
    }

    void commandsMsg() {
        out.println("Commands are: r = roll , n = next, q = quit");
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
        out.print("\nPlayer is " + player.name + " > ");
        return sc.next();
    }

    Player[] getPlayers() {
        out.print("Enter number of players > ");
        int numPlayers = sc.nextInt();

        Player[] players = new Player[numPlayers];
        for (int i = 0; i < players.length; i++) {
            out.printf("Enter player %d's name > ", i);
            String name = sc.next();

            Player p = new Player();
            p.name = name;

            players[i] = p;
        }

        return players;
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0
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



