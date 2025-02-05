package py.jona;

import java.util.Scanner;

/**
 * Para ejecutar:
 * mvn -DskipTests clean package & java -jar target\tateti-1.0-SNAPSHOT.jar
 */
public class Main{
    //Reset all attributes
    static final String RESET = "\033[0m";
    // Colors
    static final String RED = "\033[31m";
    static final String BLUE = "\033[34m";
    static final String GREEN = "\033[92m";
    static final String DEFAULT = "\033[39m";

    // Text attributes
    static final String BOLD = "\033[1m";
    static final String BLINK = "\033[5m";

    // Cursor control
    static final String CURSOR_HOME = "\033[H";
    static final String CURSOR_END = "\033[26;0H";

    //Clean
    static final String CLEAN_SCREEN = "\033[2J";
    static final String CLEAN_LINE = "\033[J";

    public static void main(String[] args) throws Exception {
        // Reset all attributes

        TerminalKeyReader keyReader = new TerminalKeyReader();

        System.out.println("Start typing (press ESC to exit):");

        while (true) {
            int key = keyReader.readKey();
            if (key == KeyType.ESCAPE) {
                System.out.println("ESC");
                break;
            }

            switch (key) {
                case KeyType.UP_ARROW:
                    System.out.println("^"); continue;
                case KeyType.DOWN_ARROW:
                    System.out.println("D");continue;
                case KeyType.LEFT_ARROW:
                    System.out.println("<-");continue;
                case KeyType.RIGHT_ARROW:
                    System.out.println("->");continue;
                case KeyType.ENTER:
                    System.out.println("Enter");continue;
                case KeyType.OTROS:
                    System.out.println("Other");
            }
        }

        System.exit(0);



        System.out.println(CLEAN_SCREEN + CURSOR_HOME);
        Scanner scanner = new Scanner(System.in);
        String [][] board = new String[][] {
            {" ", " ", " "},
            {" ", " ", " "},
            {" ", " ", " "}
        };
        printBoard(board);

        //play
        String character = "X";
        while (checkWin(board) == null) {
            System.out.println("Type the row: ");
            int i = scanner.nextInt();
            System.out.println("Type the column: ");
            int j = scanner.nextInt();
            if(invalidMove(board, i, j)) {   //if its true type the row and column again
                continue;
            }

            board[i][j] = character;
            if(board[i][j].equals("X")) {
                printBoard(board);
                character = "O";
                continue;
            }

            if(board[i][j].equals("O")) {
                printBoard(board);
                character = "X";
            }
        }
        System.exit(0);
        System.out.println(CURSOR_END + CLEAN_LINE);
    }

    static String stylePiece(String piece) {
        return (piece.equals(" ") ? " " : piece.equals("X") ? RED+BOLD+piece : BLUE+BOLD+piece) + DEFAULT;
    }

    public static void printBoard(String[][] board) {
        System.out.println(DEFAULT + CLEAN_SCREEN + CURSOR_HOME);
        System.out.println("    Positions    ");
        System.out.println();
        System.out.println("     │     │     ");
        System.out.println(" 0.0 │ 1.0 │ 2.0 ");
        System.out.println("─────┼─────┼─────");
        System.out.println(" 0.1 │ 1.1 │ 2.1");
        System.out.println("─────┼─────┼─────");
        System.out.println(" 0.2 │ 1.2 │ 2.2 ");
        System.out.println("     │     │     ");
        System.out.println();
        System.out.println("─────────────────");
        System.out.println();
        System.out.println("      Game       ");
        System.out.println();
        System.out.println("     │     │     ");
        System.out.println("  " + stylePiece(board[0][0]) + "  │  " + stylePiece(board[1][0]) + "  │  " + stylePiece(board[2][0]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][1]) + "  │  " + stylePiece(board[1][1]) + "  │  " + stylePiece(board[2][1]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][2]) + "  │  " + stylePiece(board[1][2]) + "  │  " + stylePiece(board[2][2]));
        System.out.println("     │     │     " );

        String whoWon = checkWin(board);
        if (whoWon != null) {
            System.out.println();
            System.out.println(GREEN + BLINK + "     Win: " + DEFAULT + stylePiece(whoWon) + RESET);
        }
    }

    public static boolean invalidMove(String[][] board, int i, int j) {
        if (i >= 3 || i < 0) return true;
        if (j >= 3 || j < 0) return true;
        return !board[i][j].equals(" ");
    }

    public static String checkWin(String[][] player) {
        /*
                 |     |
             0.0 | 1.0 | 2.0
            -----|-----|-----
             0.1 | 1.1 | 2.1"
            -----|-----|-----
             0.2 | 1.2 | 2.2
                 |     |
         */
        //horizontal
        if ((!player[0][0].equals(" ")) && (player[0][0].equals(player[1][0])) && (player[0][0].equals(player[2][0]))) return player[0][0];
        if ((!player[0][1].equals(" ")) && (player[0][1].equals(player[1][1])) && (player[0][1].equals(player[2][1]))) return player[0][1];
        if ((!player[0][2].equals(" ")) && (player[0][2].equals(player[1][2])) && (player[0][2].equals(player[2][2]))) return player[0][2];
        //vertical
        if ((!player[0][0].equals(" ")) && (player[0][0].equals(player[0][1])) && (player[0][0].equals(player[0][2]))) return player[0][0];
        if ((!player[1][0].equals(" ")) && (player[1][0].equals(player[1][1])) && (player[1][0].equals(player[1][2]))) return player[1][0];
        if ((!player[2][0].equals(" ")) && (player[2][0].equals(player[2][1])) && (player[2][0].equals(player[2][2]))) return player[2][0];
        //diagonal
        if ((!player[0][0].equals(" ")) && (player[0][0].equals(player[1][1])) && (player[0][0].equals(player[2][2]))) return player[0][0];
        if ((!player[2][0].equals(" ")) && (player[2][0].equals(player[1][1])) && (player[2][0].equals(player[0][2]))) return player[2][0];
        return null;
    }
}
