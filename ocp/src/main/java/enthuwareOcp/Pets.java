package enthuwareOcp;

import java.io.IOException;
import java.io.PrintWriter;

enum Pets {
    DOG("D"), CAT("C"), FISH("F");

    static final String prefix = "I am ";
    String name;

    Pets(String s) {
        name = prefix + s;
    }

    public String getData() {
        return name;
    }


    public void outputText(PrintWriter pw, String text){
        //try{
            pw.write(text);
        //}catch(IOException e){
        //    System.out.println("exception in writing");
        //}
    }
}