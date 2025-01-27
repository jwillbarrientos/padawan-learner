import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        String nombre = new String("Jonathan xd");

        StringBuilder input = new StringBuilder();
        input.append(nombre);
        System.out.println(input.reverse());

        StringBuffer input1 = new StringBuffer();
        input1.append(nombre);
        System.out.println(input1);

        StringTokenizer input2 = new StringTokenizer(nombre);
        System.out.println(input2.countTokens());
    }
}