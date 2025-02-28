package book;

import java.util.Arrays;

public class Strings {
    public static void main(String[] args) {
        String replaceTest1 = "Hola";
        replaceTest1.replace("ola", "alo");
        StringBuilder replaceTest2 = new StringBuilder("Hola");
        replaceTest2.replace(1, 4, "alo");

        System.out.println(replaceTest1);
        System.out.println(replaceTest2);

        StringBuilder sb1 = new StringBuilder("123");
        char[] name = {'J', 'a', 'v', 'a'};
        sb1.insert(1, name, 1, 3);
        System.out.println(sb1);

        String morning1 = new String("Morning");
        System.out.println("Morning" == morning1);

        String varWithSpaces = " AB CB       ";
        System.out.print(" : ");
        System.out.print(varWithSpaces);
        System.out.print(" : ");
        System.out.print(":");
        System.out.print(varWithSpaces.trim());
        System.out.println("        :".trim());

        String var1 = new String("Java");
        String var2 = "Java";
        System.out.println(var1.equals(var2));
        System.out.println(var1 == var2);
    }
}
