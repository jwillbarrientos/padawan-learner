package enthuwareOcp;

enum X { X1, X2, X3}

public class AStudyInEnum {
    public enum Y {Y1, Y2, Y3}
    public AStudyInEnum(){
        System.out.println(X.X1 + " " + Y.Y1);
    }

    public static void main(String[] args) {
        new AStudyInEnum();
    }
}
