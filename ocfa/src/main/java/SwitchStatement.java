public class SwitchStatement {
    public static void main(String[] args) {
        args = new String[]{"0", "segundo ar4g"};
        int i = 0;
        switch (args[0]) {

            default:
                i = i + 3;
            case "2":
                i = i + 2;
            case "0":
                break;
            case "1":
                i = i + 1;
        }
        System.out.println("i is " + i);
    }
}
