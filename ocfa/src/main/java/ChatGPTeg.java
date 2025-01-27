public class ChatGPTeg {
    public static void main(String[] args) {
        Person person = new Person("Alice", 18);
        changeName(person);
        System.out.println("Name: " + person.nameC);
        System.out.println("Age: " + person.ageC);
    }
    public static void changeName(Person p) {
        p.nameC = "Bob";
        p.ageC = 20;
    }

    static class Person {
        String nameC;
        int ageC;
        Person(String name, int age) {
            this.nameC = name;
            this.ageC = age;
        }
    }
}
