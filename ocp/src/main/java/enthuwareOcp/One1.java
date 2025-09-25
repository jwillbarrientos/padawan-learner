package enthuwareOcp;

public class One1 {
    One1() {
        print();
    }

    void print() {
        System.out.println("A");
    }
}

class Two2 extends One1 {
    int i = Math.round(3.5f);

    Two2() {}

    public static void main(String[] args) {
        One1 one1 = new Two2();
        one1.print();
    }

    void print() {
        System.out.println(i);
    }
}
