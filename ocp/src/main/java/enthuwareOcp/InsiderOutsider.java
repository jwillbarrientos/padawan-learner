package enthuwareOcp;

class Outsider {
    public class Insider {}
}
public class InsiderOutsider {
    public static void main(String[] args) {
        Outsider os = new Outsider();
        Outsider.Insider in = os.new Insider();
    }
}
