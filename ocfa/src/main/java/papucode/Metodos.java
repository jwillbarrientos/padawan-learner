package papucode;

public class Metodos {

    static int b = 2;

    public static void main(String[] args) {
        int a = 1;
//        int d = metodo(a);
//        metodo(a);
//        System.out.println(a);

        int f = metodoB(3);
        System.out.println(f);
        System.out.println(b);
    }
    static int metodoB(int c) {
//        b = 5;
        System.out.println(c);
        return 32+c;
    }

     static void metodo(int a) {
        a = 8;
        System.out.println(a);
    }
}