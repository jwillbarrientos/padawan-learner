package book;

public class NullPointerException {
    public static void main(String[] args) {
        String[][] oldLaptops = {{"Dell", "Toshiba", "Vaio"}, null, {"IBM"}, new String[10]};
        System.out.println(oldLaptops[0][0]);
        System.out.println(oldLaptops[1]);
        System.out.println(oldLaptops[3][6]);
        System.out.println(oldLaptops);
        //This is the only statement that throws a NullPointerException
        System.out.println(oldLaptops[3][0].length());
    }
}
