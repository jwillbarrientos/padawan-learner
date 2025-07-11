package book;

import java.util.Optional;

public class OptionalExample {
    public static Optional<Double> average(int... scores) {
        if(scores.length == 0) return Optional.empty();
        int sum = 0;
        for(int score : scores) sum += score;
        return Optional.of((double) sum / scores.length);
    }

    public static void main(String[] args) {
        System.out.println(average(90, 100));
        System.out.println(average());

        Optional<Double> opt = average(90, 100);
        if(opt.isPresent())
            System.out.println(opt.get());

        //Optional<Double> opt2 = average();
        //System.out.println(opt2.get());   //throws java.util.NoSuchElementException: No value present

        Optional<Double> value = average();
        Optional o = (value == null) ? Optional.empty() : Optional.of(value);
        Optional o2 = Optional.ofNullable(value);

        System.out.println(o);
        System.out.println(o2);

        Optional<Double> opt3 = average(90, 100);
        opt3.ifPresent(System.out::println);

        Optional<Double> opt4 = average(90, 95);
        System.out.println(opt4.orElse(Double.NaN));
        System.out.println(opt4.orElseGet(() -> Math.random())); // or Math::random
        System.out.println(opt4.orElseThrow(IllegalArgumentException::new)); // or () -> new IllegalArgumentException();

        Optional<Double> opt5 = average();
        System.out.println(opt5.orElse(Double.NaN));
        System.out.println(opt5.orElseGet(() -> Math.random())); // or Math::random
        System.out.println(opt5.orElseThrow(IllegalArgumentException::new)); // or () -> new IllegalArgumentException();
    }
}
