package py.jona;

import static py.jona.KeyType.*;

/**
 * Para ejecutar:
 * mvn -DskipTests clean package & java -jar target\tateti-1.0-SNAPSHOT.jar
 * mvn "-DskipTests" clean package ; java "-jar" target\tateti-1.0-SNAPSHOT.jar
 */
public class Main{

    static String currentPlayer = "X";

    static TerminalHelper thelper;

    static String[][] board = new String[][]{
            {" ", " ", " "},
            {" ", " ", " "},
            {" ", " ", " "}
    };

    public static void main(String[] args) throws Exception {
        thelper = new TerminalHelper();

        thelper.cls();
        thelper.setCursorBlock();



        printBoard();
        printNextPlayerIfNotWon();
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
                    if (invalidMove(i, j)) continue;
                    board[i][j] = currentPlayer;
                    if (currentPlayer.equals("X")) {
                        thelper.moveCursor(8, 5);
                        currentPlayer = "O";
                    } else {
                        thelper.moveCursor(8, 5);
                        currentPlayer = "X";
                    }
                    printBoard();
                    printNextPlayerIfNotWon();
                    thelper.moveCursor(row,column);
                    break;
                }
            }
        } while (checkWin() == null);
        thelper.moveCursor(8, 5);
        String whoWon = checkWin();
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

    public static void printBoard() {
        thelper.cls();
        System.out.println("     │     │     ");
        System.out.println("  " + stylePiece(board[0][0]) + "  │  " + stylePiece(board[1][0]) + "  │  " + stylePiece(board[2][0]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][1]) + "  │  " + stylePiece(board[1][1]) + "  │  " + stylePiece(board[2][1]));
        System.out.println("─────┼─────┼─────");
        System.out.println("  " + stylePiece(board[0][2]) + "  │  " + stylePiece(board[1][2]) + "  │  " + stylePiece(board[2][2]));
        System.out.println("     │     │     " );

    }

    private static void printNextPlayerIfNotWon() {
        if (checkWin() == null) {
            if (currentPlayer.equals("X")) {
                thelper.moveCursor(8, 5);
                thelper.printWithColors("X", TerminalHelper.RED, false);
            } else {
                thelper.moveCursor(8, 5);
                thelper.printWithColors("O", TerminalHelper.BLUE, false);
            }
            System.out.print(" plays");
        }
    }

    public static boolean invalidMove(int i, int j) {
        return !board[i][j].equals(" ");
    }


    public static String checkWin() {
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
        if ((!board[0][0].equals(" ")) && (board[0][0].equals(board[1][0])) && (board[0][0].equals(board[2][0]))) return board[0][0];
        if ((!board[0][1].equals(" ")) && (board[0][1].equals(board[1][1])) && (board[0][1].equals(board[2][1]))) return board[0][1];
        if ((!board[0][2].equals(" ")) && (board[0][2].equals(board[1][2])) && (board[0][2].equals(board[2][2]))) return board[0][2];
        //vertical
        if ((!board[0][0].equals(" ")) && (board[0][0].equals(board[0][1])) && (board[0][0].equals(board[0][2]))) return board[0][0];
        if ((!board[1][0].equals(" ")) && (board[1][0].equals(board[1][1])) && (board[1][0].equals(board[1][2]))) return board[1][0];
        if ((!board[2][0].equals(" ")) && (board[2][0].equals(board[2][1])) && (board[2][0].equals(board[2][2]))) return board[2][0];
        //diagonal
        if ((!board[0][0].equals(" ")) && (board[0][0].equals(board[1][1])) && (board[0][0].equals(board[2][2]))) return board[0][0];
        if ((!board[2][0].equals(" ")) && (board[2][0].equals(board[1][1])) && (board[2][0].equals(board[0][2]))) return board[2][0];
        //draw
        if ((!board[0][0].equals(" ")) && (!board[1][0].equals(" ")) && (!board[2][0].equals(" ")) && (!board[0][1].equals(" ")) && (!board[1][1].equals(" ")) && (!board[2][1].equals(" ")) && (!board[0][2].equals(" ")) && (!board[1][2].equals(" ")) && (!board[2][2].equals(" "))) return "Draw";
        return null;
    }
}
