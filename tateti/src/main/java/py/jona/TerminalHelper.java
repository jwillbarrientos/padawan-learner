package py.jona;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

public class TerminalHelper implements AutoCloseable {
    private final Terminal terminal;
    private final NonBlockingReader reader;

    // Constructor sets up the terminal in raw mode
    public TerminalHelper() throws IOException {
        this.terminal = TerminalBuilder.builder()
                .jna(true)
                .system(true)
                .build();
//        terminal.
        this.terminal.enterRawMode();
        reader = terminal.reader();

       // Runtime.getRuntime().addShutdownHook(new Thread(()-> {
       //     try {
       //         System.out.println("exiting...");
       //         System.out.flush();
       //         Thread.sleep(1000);
       //         System.out.println("\033[0 q\033[H\033[2J"); // restore cursor // cursor home // clean screen
       //         System.out.flush();
       //         reader.close();
       //         terminal.close();
       //     } catch (Exception e) {
       //         e.printStackTrace();
       //     }
       // }));
    }

    public void moveCursor(int row, int column) {
        terminal.puts(InfoCmp.Capability.cursor_address, row, column);
        terminal.flush();
    }
    public void cls() {
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.flush();
    }

    public void setCursorBlock() {
        terminal.writer().print("\033[1 q");
        terminal.writer().flush();
    }

    // Main method to read a key and return the corresponding KeyType
    public int readKey() throws IOException, InterruptedException {
        int firstChar = reader.read();
//        System.out.println("reader.read(): first char"+ firstChar);

        if (firstChar == '\n' || firstChar == '\r') { // Handle enter key
            return KeyType.ENTER;
        }

        // Handle escape sequences (arrow keys)
        if (firstChar == 27) { // ESC character
            // Try to read the next characters with a small timeout
            // This helps distinguish between actual ESC key and escape sequences
            if (!reader.ready()) {
                return KeyType.ESCAPE;
            }

            int secondChar = reader.read();
//            System.out.println("reader.read(): secondChar"+ secondChar);
            if (secondChar == 79) { // '['
                int thirdChar = reader.read();
//                System.out.println("reader.read(): thirdChar"+ thirdChar);
                switch (thirdChar) {
                    case 65: return KeyType.UP_ARROW;
                    case 66: return KeyType.DOWN_ARROW;
                    case 67: return KeyType.RIGHT_ARROW;
                    case 68: return KeyType.LEFT_ARROW;
                    default: {
                        while (reader.ready()) reader.read();
                        return KeyType.OTROS;
                    }
                }
            } else {
                while (reader.ready()) reader.read();
            }

        }

        return KeyType.OTROS; // any other character
    }

    // Properly close resources
    @Override
    public void close() throws IOException {
        reader.close();
        terminal.close();
    }
}