package book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TryWithResources {
    public void oldApproach(Path path1, Path path2) throws Exception {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            in = Files.newBufferedReader(path1);
            out = Files.newBufferedWriter(path2);
            out.write(in.readLine());
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }

        try (StuckTurkeyCage t = new StuckTurkeyCage()) { // DOES NOT COMPILE
            System.out.println("put turkeys in");
        }
    }

    public static class StuckTurkeyCage implements AutoCloseable {
        public void close() throws Exception {
            throw new Exception("Cage door does not close");
        }
    }

    public void newApproach(Path path1, Path path2) throws IOException {
        try (BufferedReader in = Files.newBufferedReader(path1);
             BufferedWriter out = Files.newBufferedWriter(path2)) {
            out.write(in.readLine());
        }
    }
}
