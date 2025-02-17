package py.jona;

import static py.jona.KeyType.*;

/**
 * Para ejecutar:
 * mvn -DskipTests clean package & java -jar target\tateti-1.0-SNAPSHOT.jar
 * mvn "-DskipTests" clean package ; java "-jar" target\tateti-1.0-SNAPSHOT.jar
 */
public class Main{

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

        printBoard(board, character);
        printNextPlayerIfNotWon(board, character);
        int i = 0;
        int j = 0;
        int row = 1;
        int column = 2;
        thelper.moveCursor(row, column);
        do {
            while (true) {
                int key = thelper.readKey();
                if (key == UP_ARROW) {
                    if (row == 1) continue;
                    j--;
                    thelper.moveCursor(row - 2, column);
                    row -= 2;
                    continue;
                }
                if (key == DOWN_ARROW) {
                    if (row == 5) continue;
                    j++;
                    thelper.moveCursor(row + 2, column);
                    row += 2;
                    continue;
                }
                if (key == RIGHT_ARROW) {
                    if (column == 14) continue;
                    i++;
                    thelper.moveCursor(row, column + 6);
                    column += 6;
                    continue;
                }
                if (key == LEFT_ARROW) {
                    if (column == 2) continue;
                    i--;
                    thelper.moveCursor(row, column - 6);
                    column -= 6;
                    continue;
                }
                if (key == ENTER) {
                    if (invalidMove(board, i, j)) continue;
                    board[i][j] = character;
                    if (character.equals("X")) {
                        thelper.moveCursor(8, 5);
                        character = "O";
                    } else {
                        thelper.moveCursor(8, 5);
                        character = "X";
                    }
                    printBoard(board, character);
                    printNextPlayerIfNotWon(board, character);
                    thelper.moveCursor(row,column);
                    break;
                }
            }
        } while (checkWin(board) == null);
        thelper.moveCursor(8, 5);
        String whoWon = checkWin(board);
        if (whoWon.equals("Draw")) {
            thelper.printWithColors(" Draw\n", TerminalHelper.SKY_BLUE, true);
        }
        if (whoWon.equals("X") || whoWon.equals("O")) {
            thelper.printWithColors("Win: "+stylePiece(whoWon)+"\n", TerminalHelper.GREEN, true);
        }
    }

    static String stylePiece(String piece) {
        return (piece.equals(" ") ? " " : piece.equals("X") ? TerminalHelper.RED+TerminalHelper.BOLD+piece : TerminalHelper.BLUE+TerminalHelper.BOLD+piece) + TerminalHelper.DEFAULT;
    }

    public static void printBoard(String[][] board, String character) {
        thelper.cls();
        System.out.println("     │     │     ");
        System.out.println("  " + stylePiece(board[0][0]) + "  │  " + stylePiece(board[1][0]) + "  │  " + stylePiece(board[2][0]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][1]) + "  │  " + stylePiece(board[1][1]) + "  │  " + stylePiece(board[2][1]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][2]) + "  │  " + stylePiece(board[1][2]) + "  │  " + stylePiece(board[2][2]));
        System.out.println("     │     │     " );

    }

    private static void printNextPlayerIfNotWon(String[][] board, String character) {
        if (checkWin(board) == null) {
            if (character.equals("X")) {
                thelper.moveCursor(8, 5);
                thelper.printWithColors("X", TerminalHelper.RED, false);
            } else {
                thelper.moveCursor(8, 5);
                thelper.printWithColors("O", TerminalHelper.BLUE, false);
            }
            System.out.print(" plays");
        }
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
        //draw
        if ((!player[0][0].equals(" ")) && (!player[1][0].equals(" ")) && (!player[2][0].equals(" ")) && (!player[0][1].equals(" ")) && (!player[1][1].equals(" ")) && (!player[2][1].equals(" ")) && (!player[0][2].equals(" ")) && (!player[1][2].equals(" ")) && (!player[2][2].equals(" "))) return "Draw";
        return null;
    }
}
