package py.jona;

/**
 * Para ejecutar:
 * mvn -DskipTests clean package & java -jar target\tateti-1.0-SNAPSHOT.jar
 * mvn "-DskipTests" clean package ; java "-jar" target\tateti-1.0-SNAPSHOT.jar
 */
public class Main{

    enum Player {
        X(Colors.RED),
        O(Colors.BLUE);
        public final Colors color;
        Player(Colors color) {
            this.color = color;
        }
    }

    static Player cPlayer = Player.X;
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

        int i = 0;
        int j = 0;
        int row = 1;
        int column = 2;
        int plays = 0;
        Player won = null;
        do {
            printBoard();
            printNextPlayerIfNotWon();
            thelper.moveCursor(row, column);

            switch (thelper.readKey()) {
                case UP_ARROW:
                    if (row == 1) continue;
                    j--;
                    thelper.moveCursor(row - 2, column);
                    row -= 2;
                    continue;
                case DOWN_ARROW:
                    if (row == 5) continue;
                    j++;
                    thelper.moveCursor(row + 2, column);
                    row += 2;
                    continue;
                case RIGHT_ARROW :
                    if (column == 14) continue;
                    i++;
                    thelper.moveCursor(row, column + 6);
                    column += 6;
                    continue;
                case LEFT_ARROW:
                    if (column == 2) continue;
                    i--;
                    thelper.moveCursor(row, column - 6);
                    column -= 6;
                    continue;
                case ENTER:
                    if (invalidMove(i, j)) continue;
                    plays++;
                    board[i][j] = cPlayer.name();
                    cPlayer = cPlayer == Player.X ? Player.O : Player.X;
            }

            won = checkWin();
        } while (won == null && plays < 9);

        printBoard();
        thelper.moveCursor(8, 5);
        if (won == null) {
            thelper.printWithColors(" Draw\n", Colors.SKY_BLUE, true);
        } else {
            thelper.printWithColors("Win: "+ stylePiece(won.name()) +"\n", Colors.GREEN, true);
        }
    }

    static String stylePiece(String piece) {
        if (piece.equals(" "))
            return " "+ Colors.DEFAULT.escapeSequence;
        if (piece.equals("X"))
            return Colors.RED.escapeSequence+TerminalHelper.BOLD + piece + Colors.DEFAULT.escapeSequence;
        return Colors.BLUE.escapeSequence+TerminalHelper.BOLD+piece + Colors.DEFAULT.escapeSequence;
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
            thelper.moveCursor(8, 5);
            thelper.printWithColors(cPlayer.name(), cPlayer.color, false);
            System.out.println(" plays");
        }
    }

    public static boolean invalidMove(int i, int j) {
        return !board[i][j].equals(" ");
    }

    static Player checkWin() {
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
        if ((!board[0][0].equals(" ")) && (board[0][0].equals(board[1][0])) && (board[0][0].equals(board[2][0])))
            return Player.valueOf(board[0][0]);
        if ((!board[0][1].equals(" ")) && (board[0][1].equals(board[1][1])) && (board[0][1].equals(board[2][1])))
            return Player.valueOf(board[0][1]);
        if ((!board[0][2].equals(" ")) && (board[0][2].equals(board[1][2])) && (board[0][2].equals(board[2][2])))
            return Player.valueOf(board[0][2]);

        //vertical
        if ((!board[0][0].equals(" ")) && (board[0][0].equals(board[0][1])) && (board[0][0].equals(board[0][2])))
            return Player.valueOf(board[0][0]);
        if ((!board[1][0].equals(" ")) && (board[1][0].equals(board[1][1])) && (board[1][0].equals(board[1][2])))
            return Player.valueOf(board[1][0]);
        if ((!board[2][0].equals(" ")) && (board[2][0].equals(board[2][1])) && (board[2][0].equals(board[2][2])))
            return Player.valueOf(board[2][0]);

        //diagonal
        if ((!board[0][0].equals(" ")) && (board[0][0].equals(board[1][1])) && (board[0][0].equals(board[2][2])))
            return Player.valueOf(board[0][0]);
        if ((!board[2][0].equals(" ")) && (board[2][0].equals(board[1][1])) && (board[2][0].equals(board[0][2])))
            return Player.valueOf(board[2][0]);

        return null;
    }
}
