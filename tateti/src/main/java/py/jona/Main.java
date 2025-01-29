package py.jona;

import java.util.Scanner;

public class Main{
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

    //Clean
    static final String CLEAN_SCREEN = "\033[2J";

    public static void main(String[] args) {
        // Reset all attributes


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
            if (itsInvalidMove(i, j) || itsNotVoid(board, i, j)) {   //if its true type the row and column again
                continue;
            }
            if (board[i][j].equals("O")){
                printBoard(board);
            }
            board[i][j] = character;
            if (board[i][j].equals("X")){
                printBoard(board);
            }

            if (checkWin(board) != null) {
                continue;
            }

            System.out.println("Type the row: ");
            i = scanner.nextInt();
            System.out.println("Type the column: ");
            j = scanner.nextInt();
            if (itsInvalidMove(i, j) || itsNotVoid(board, i, j)) {   //if its true type the row and column again
                board[i][j] = "O";
                continue;
            }
            if (board[i][j].equals("O")){
                printBoard(board);
            }
            board[i][j] = "X";
            if (board[i][j].equals("X")){
                printBoard(board);
            }
        }
        System.out.println("Finish");
    }

    static String stylePiece(String piece) {
        return (piece.equals(" ") ? " " : piece.equals("X") ? RED+BOLD+piece : BLUE+BOLD+piece) + DEFAULT;
    }

    public static void printBoard(String[][] board) {
        System.out.println(DEFAULT + CLEAN_SCREEN + CURSOR_HOME);
        System.out.println("     |     |     ");
        System.out.println(" 0.0 | 1.0 | 2.0 ");
        System.out.println("-----|-----|-----");
        System.out.println(" 0.1 | 1.1 | 2.1");
        System.out.println("-----|-----|-----");
        System.out.println(" 0.2 | 1.2 | 2.2 ");
        System.out.println("     |     |     ");
        System.out.println();
        System.out.println("     |     |     ");
        System.out.println("  " + stylePiece(board[0][0]) + "  |  " + stylePiece(board[1][0]) + "  |  " + stylePiece(board[2][0]));
        System.out.println("-----|-----|-----");
        System.out.println("  " + stylePiece(board[0][1]) + "  |  " + stylePiece(board[1][1]) + "  |  " + stylePiece(board[2][1]));
        System.out.println("-----|-----|-----");
        System.out.println("  " + stylePiece(board[0][2]) + "  |  " + stylePiece(board[1][2]) + "  |  " + stylePiece(board[2][2]));
        System.out.println("     |     |     " );

        String whoWon = checkWin(board);
        if (whoWon != null) {
            System.out.println(GREEN + BLINK + "Win: " + DEFAULT + stylePiece(whoWon));
        }
    }

    public static boolean itsNotVoid(String[][] board, int i, int j) {
        if (board[i][j].equals(" ")) return false;
        return true;
    }

    public static boolean itsInvalidMove(int i, int j) {
        if (i > 3) return true;
        if (j > 3) return true;
        return false;
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
