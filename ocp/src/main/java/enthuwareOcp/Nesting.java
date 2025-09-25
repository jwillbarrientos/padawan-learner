package enthuwareOcp;

public class Nesting {
    public static void main(String[] args) {
        Two.Three obj = new Two().new Three();
    }
}

class One {
    char three;
    One(char three) {
        this.three = three;
    }
}

class Two extends One {
    char three = '1';
    Two() {
        super('2');
    }

    class Three extends One {
        char three = '3';
        Three() {
            super('4');
            System.out.println(Two.this.three);
            System.out.println(Three.this.three);
            System.out.println(super.three);
        }
    }
}
