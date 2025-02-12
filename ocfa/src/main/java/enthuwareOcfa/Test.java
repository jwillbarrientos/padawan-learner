package enthuwareOcfa;

class Data {
    int value = 100;
}
public class Test {
    public static void main(String[] args) {
        modifyData(new Data());
    }

    public static void modifyData(Data x) {
        x.value = 2 * x.value;
        System.out.println(x.value);
    }
}
