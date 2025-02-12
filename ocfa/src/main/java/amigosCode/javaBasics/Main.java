package amigosCode.javaBasics;

public class Main {

    public static void main(String[] args) {
        Person alex = new Person("alex", 20);
        Person mariam = alex;

        System.out.println("before changing alex");
        System.out.println(alex.nameC + " " + alex.ageC + " - " + mariam.nameC + " " + mariam.ageC);

        mariam.nameC = "Mariam";
        mariam.ageC = 10;

        System.out.println("after changing alex");
        System.out.println(alex.nameC + " " + alex.ageC + " - " + mariam.nameC + " " + mariam.ageC);

        boolean igual = false;

        Person nuevaPersona = new Person("Mariam", 10);

        igual = nuevaPersona.soyIgualALosdValoresDeOtraPersona(alex.nameC, alex.ageC);
        System.out.println("iguales? "+ igual);
        nuevaPersona.nameC = "Alex";
        igual = nuevaPersona.soyIgualALosdValoresDeOtraPersona(alex.nameC, alex.ageC);
        System.out.println("iguales? "+ igual);

    }

    static class Person {
        String nameC;
        int ageC;

        Person(String name, int ageC) {
            nameC = name;
            this.ageC = ageC;
        }

        public boolean soyIgualALosdValoresDeOtraPersona(String nameC, int ageC) {
            if (this.ageC == ageC && this.nameC.equals(nameC)) {
                return true;
            } else {
                return false;
            }
        }
    }
}