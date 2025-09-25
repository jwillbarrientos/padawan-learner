package enthuwareOcp;

interface Eatable {
    int types = 10;
}

class Food1 implements Eatable {
    public static int types = 20;
}

//if you implement Eatable in Fruit will not work
public class Fruit extends Food1 {
    public static void main(String[] args) {
        types = 30;
        System.out.println(types);
    }
}
