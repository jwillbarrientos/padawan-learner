package enthuwareOcp;

public class EnumTest {
    public static void main(String[] args) {
        Color c1 = Enum.valueOf(Color.class, "RED");
        Color c2 = Color.valueOf("BLUE");
        //Color c3 = Color.valueOf("blue");
        Color c4 = Color.GREEN;
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c4.name());
        System.out.println(c4.toString());
    }
}

enum Color { RED, GREEN, BLUE }
