package inheritance;

public class Computers extends Student {
    int x = 6;
    void studies() {
        System.out.println("Studying Computers");
        System.out.println(super.x);
    }
    public static void main(String[] args) {
        Computers p = new Computers();
        p.enrolled();
        p.studies();
    }
}