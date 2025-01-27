package papu.code.foo;

import java.time.LocalDate;

public class ClassPassport {
    public static void main(String[] args) {
        Passport uSAPassport = new Passport(4523, "USA", LocalDate.of(2007, 04, 21), true);
        Passport uKPassport = new Passport(9749, "UK", LocalDate.of(2014, 04, 07), false);
        Passport pYPassport = new Passport(8321, "PY", LocalDate.of(2018, 05, 01), true);

        System.out.println("USA Passport");
        System.out.println("Number: " + uSAPassport.numberC);
        System.out.println("Country: " + uSAPassport.countryC);
        System.out.println("Birthdate: " + uSAPassport.localDateC);
        System.out.println("Covid Vaccine: " + uSAPassport.covidVaccineC);
        System.out.println();

        System.out.println("UK Passport");
        System.out.println("Number: " + uKPassport.numberC);
        System.out.println("Country: " + uKPassport.countryC);
        System.out.println("Birthdate: " + uKPassport.localDateC);
        System.out.println("Covid Vaccine: " + uKPassport.covidVaccineC);
        System.out.println();

        System.out.println("PY Passport");
        System.out.println("Number: " + pYPassport.numberC);
        System.out.println("Country: " + pYPassport.countryC);
        System.out.println("Birthdate: " + pYPassport.localDateC);
        System.out.println("Covid Vaccine: " + pYPassport.covidVaccineC);
    }
    static class Passport {
        int numberC;
        String countryC;
        LocalDate localDateC;
        boolean covidVaccineC;

        Passport(int number, String country, LocalDate birthdate, boolean covidVaccine) {
            this.numberC = number;
            this.countryC = country;
            this.localDateC = birthdate;
            this.covidVaccineC = covidVaccine;
        }
    }
}