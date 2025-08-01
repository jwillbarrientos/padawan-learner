package book;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

public class localeExamples {
    public static void main(String[] args) throws ParseException {
        Locale locale = Locale.getDefault();
        System.out.println(locale);

        Locale us = new Locale("en", "US");
        Locale france = new Locale("fr", "FR");
        System.out.println(us);
        System.out.println(france);

        NumberFormat us1 = NumberFormat.getInstance(Locale.US);
        System.out.println(us1.format(1000.25));

        NumberFormat us2 = NumberFormat.getInstance(Locale.US);
        Number n = us2.parse("1,000.25");
        System.out.println(n);

        printProperties(us);
        System.out.println();
        printProperties(france);

        LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
        LocalTime time = LocalTime.of(11, 12, 34);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm");
        System.out.println(dateTime.format(f));
        DateTimeFormatter fx = DateTimeFormatter.ofPattern("MM dd yyyy");
        LocalDate date2 = LocalDate.parse("01 02 2015", fx);
        System.out.println(date); // 2015–01–02
        System.out.println(date.format(fx));

        ZoneId zone = ZoneId.of("US/Eastern");
        LocalDate date3 = LocalDate.of(2016, 3, 13);
        LocalTime time1 = LocalTime.of(2, 15);
        ZonedDateTime a = ZonedDateTime.of(date3, time1, zone);
        System.out.println(a);

        String m1 = Duration.of(1, ChronoUnit.MINUTES).toString();
        String m2 = Duration.ofMinutes(1).toString();
        String s = Duration.of(60, ChronoUnit.SECONDS).toString();
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(s);
    }

    public static void printProperties(Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle("book.Zoo", locale);
        System.out.println(rb.getString("hello"));
        System.out.println(rb.getString("open"));
    }
}
