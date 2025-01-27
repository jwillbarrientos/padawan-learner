package py.jona;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        // Reset all attributes
        final String RESET = "\033[0m";

        // Colors
        final String RED = "\033[31m";
        final String BLUE = "\033[34m";

        // Text attributes
        final String BOLD = "\033[1m";
        final String BLINK = "\033[5m";

        // Cursor control
        final String CURSOR_INITAL = "\033[2;0H";
        final String CURSOR_POS = "\033[6;2H";

        //Clean
        final String CLEAN_SCREEN = "\033[2J";
        final String CLEAN_LINE = "\033[2K";

        System.out.println(CLEAN_SCREEN + CURSOR_INITAL + "Type the player one: x | o");
        Scanner scanner = new Scanner(System.in);
        char playerOne = scanner.next().charAt(0);
        System.out.println("The Player One its: " + firstPlayer(playerOne));
        if (firstPlayer(playerOne) == 'x') {
            System.out.println("And the Second its: o");
            char playerTwo = 'o';
        }
        if (firstPlayer(playerOne) == 'o') {
            System.out.println("And the Second its: x");
            char playerTwo = 'x';
        }
        boardInitializer(CURSOR_POS);

        char [][] board = new char[3][3];
        winOrNot(board);
    }
    public static void boardInitializer (String CURSOR_POS) {
        System.out.println("   |   |   ");
        System.out.println("   |   |   ");  //playable
        System.out.println("   |   |   ");
        System.out.println("___|___|___");
        System.out.println("   |   |   ");
        System.out.println("   |   |   ");  //playable
        System.out.println("   |   |   ");
        System.out.println("___|___|___");
        System.out.println("   |   |   ");
        System.out.println("   |   |   ");  //playable
        System.out.println("   |   |   ");
    }

    public static char firstPlayer (char player) {
        try {
            itsValid(player);
        } catch (IllegalArgumentException iae) {
            System.out.println("The character its not valid");
        }
        return player;
    }

    public static void itsValid (char player) {
        if (player == 'x') {
            player  = 'x';
        } else if (player == 'o') {
            player = 'o';
        } else throw new IllegalArgumentException();
    }

    public static boolean winOrNot (char[][] chars) {
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                //from left to right
                if ((chars[0][0] == chars[1][0]) && (chars[0][0] == chars[2][0])) {
                    return true;
                }
                if ((chars[0][1] == chars[1][1]) && (chars[0][1] == chars[2][1])) {
                    return true;
                }
                if ((chars[0][2] == chars[1][2]) && (chars[0][2] == chars[2][2])) {
                    return true;
                }

                //from up to down
                if ((chars[0][0] == chars[0][1]) && (chars[0][0] == chars[0][2])) {
                    return true;
                }
                if ((chars[1][0] == chars[1][1]) && (chars[1][0] == chars[1][2])) {
                    return true;
                }
                if ((chars[2][0] == chars[1][2]) && (chars[2][0] == chars[2][2])) {
                    return true;
                }

                //diagonal
                if ((chars[0][0] == chars[1][1]) && (chars[0][0] == chars[2][2])) {
                    return true;
                }
            }
        }
        return false;
    }
}
