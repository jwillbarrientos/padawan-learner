package py.jona;

import static py.jona.KeyType.*;

/**
 * Para ejecutar:
 * mvn -DskipTests clean package & java -jar target\tateti-1.0-SNAPSHOT.jar
 * mvn "-DskipTests" clean package ; java "-jar" target\tateti-1.0-SNAPSHOT.jar
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

    // Clean attributes
    static final String CLEAN_SCREEN = "\033[2J";
    static String character = "X";

    static TerminalHelper thelper;

    public static void main(String[] args) throws Exception {
        thelper = new TerminalHelper();

        thelper.cls();
        thelper.setCursorBlock();

        String[][] board = new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}
        };

        printBoard(board);
        thelper.moveCursor(1,2);
        int i = 0;
        int j = 0;
        int row = 1;
        int column = 2;
        do {
            while (true) {
                int key = thelper.readKey();
                if (key == UP_ARROW) {
                    if (row == 1) {
                        continue;
                    } else {
                        j--;
                        thelper.moveCursor(row - 2, column);
                        row -= 2;
                        continue;
                    }
                }
                if (key == DOWN_ARROW) {
                    if (row == 5) {
                        continue;
                    } else {
                        j++;
                        thelper.moveCursor(row + 2, column);
                        row += 2;
                        continue;
                    }
                }
                if (key == RIGHT_ARROW) {
                    if (column == 14) {
                        continue;
                    } else {
                        i++;
                        thelper.moveCursor(row, column + 6);
                        column += 6;
                        continue;
                    }
                }
                if (key == LEFT_ARROW) {
                    if (column == 0) {
                        continue;
                    } else {
                        i--;
                        thelper.moveCursor(row, column - 6);
                        column -= 6;
                        continue;
                    }
                }
                if (key == ENTER) {
                    if (invalidMove(board, i, j)) continue;
                    board[i][j] = character;
                    printBoard(board);
                    if (character.equals("X")) {
                        character = "O";
                    } else {
                        character = "X";
                    }
                    thelper.moveCursor(row,column);
                    break;
                }
            }
        } while (checkWin(board) == null);
        thelper.moveCursor(8, 5);
        String whoWon = checkWin(board);
        if (whoWon != null) {
            System.out.println(GREEN + BLINK + "Win: " + DEFAULT + stylePiece(whoWon) + RESET);
        }
    }

    static String stylePiece(String piece) {
        return (piece.equals(" ") ? " " : piece.equals("X") ? RED+BOLD+piece : BLUE+BOLD+piece) + DEFAULT;
    }

    public static void printBoard(String[][] board) {
        thelper.cls();
        System.out.println("     │     │     ");
        System.out.println("  " + stylePiece(board[0][0]) + "  │  " + stylePiece(board[1][0]) + "  │  " + stylePiece(board[2][0]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][1]) + "  │  " + stylePiece(board[1][1]) + "  │  " + stylePiece(board[2][1]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][2]) + "  │  " + stylePiece(board[1][2]) + "  │  " + stylePiece(board[2][2]));
        System.out.println("     │     │     " );
    }

    public static boolean invalidMove(String[][] board, int i, int j) {
        return !board[i][j].equals(" ");
    }

    public static String checkWin(String[][] player) {
        /*
                  │     │
              0.0 │ 1.0 │ 2.0
             ─────┼─────┼─────
              0.1 │ 1.1 │ 2.1"
             ─────┼─────┼─────
              0.2 │ 1.2 │ 2.2
                  │     │
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
