package book;
import static book.A.Toucan;

public class TestPossibleBookError {
    public static void main(String[] args) {
        HeavyAnimal hippo = new Hippo();
        boolean xd = hippo instanceof Mother;
        boolean xd1 = hippo instanceof Elephant;
        System.out.println(xd);
        System.out.println(xd1);
    }
    public interface Mother {}
    static class HeavyAnimal {}
    static class Hippo extends HeavyAnimal {}
    static class Elephant extends HeavyAnimal {}
}
