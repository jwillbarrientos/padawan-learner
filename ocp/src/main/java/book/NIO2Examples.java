package book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class NIO2Examples {
    public static void main(String[] args) {
        try {
            final Path path = Paths.get("/rabbit/food.jpg");
            System.out.println(Files.getLastModifiedTime(path).toMillis());
            Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
            System.out.println(Files.getLastModifiedTime(path).toMillis());
        } catch (IOException e) {
            // Handle file I/O exception...
        }

        Path path1 = Paths.get("/user/.././root","../kodiacbear.txt");
        Path path2 = Paths.get("/lion");
        System.out.println(path1);
        System.out.println(path1.normalize().relativize(path2));

        Path base = Paths.get("/animals");
        System.out.println(base.resolve("lion"));
        System.out.println(base.resolve("/zoo/elephant"));

        System.out.println(Paths.get(".").normalize().getNameCount());
    }
}
