package book;

import java.io.File;

public class FileClass {
    public static void main(String[] args) {
        File parent = null;
        File child = new File(parent,"data/zoo.txt");
        System.out.println(child);

        parent = new File("/home/smith");
        child = new File(parent, child.toString());
        System.out.println(child);

        //print if the file exist or not
        System.out.println(child.exists());

        //both print the separator character of the system
        System.out.println(System.getProperty("file.separator"));
        System.out.println(java.io.File.separator);
    }
}
