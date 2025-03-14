package book;

public class ThisAndSuper {
    public static void main(String[] args) {
        Employers employer = new Employers();
        System.out.println(employer.name + ", " + employer.address);
        employer.name = "Jona";
        employer.address = "Asuncion";
        System.out.println(employer.name + ", " + employer.address);

        Employers employer2 = new Employers("Alan", "Asuncion");
        System.out.println(employer2.name + ", " + employer2.address);
    }
}

class Employers {
    String name;
    String address;
    Employers() {
        name = "NoName";
        address = "NoAddress";
    }

    Employers(String name, String address) {
        this();
        if (name != null) this.name = name;
        if (address != null) this.address = address;
    }
}

class Programmer extends Employers {
    String progLanguage;
    Programmer(String name, String address, String progLanguage) {
        super(name, address);
        this.progLanguage = progLanguage;
    }
}
