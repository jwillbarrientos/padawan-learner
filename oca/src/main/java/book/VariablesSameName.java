package book;

public class VariablesSameName {
    public static void main(String[] args) {
        Phone p1 = new Phone();
        p1.setNumber();
        System.out.println(p1.phoneNumber);
    }
    static class Phone {
        String phoneNumber = "123456789";
        void setNumber() {
            String phoneNumber;
            phoneNumber = "987654321";
        }
    }
}
