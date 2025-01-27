package papucode;

public class Personxd {
    int id;
    String name;
    java.util.Date dob;
    boolean VIP;
    public static void main(String[] args) {
        Personxd p1 = new Personxd();
        Personxd p2 = p1;
        int id2 = p2.id;
        p1.name = args[0];
    }
    public String getName() {
        return name;
    }
}
