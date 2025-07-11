package book;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streaming {
    public static void main(String[] args) {
        Stream<String> empty = Stream.empty();
        Stream<Integer> singleElement = Stream.of(1);
        Stream<Integer> fromArray = Stream.of(1, 2, 3);

        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> fromList = list.stream();
        Stream<String> fromListParallel = list.parallelStream();

        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);

        Stream<String> stream = Stream.of("w", "o", "l", "f");
        StringBuilder word = stream.collect(StringBuilder::new,
                StringBuilder::append, StringBuilder::append);
        System.out.println(word);

        Stream<String> s = Stream.of("brown bear-", "grizzly-");
        s.sorted(reverseOrder())
                .forEach(System.out::print);

        //s.sorted(( c1,  c2) -> c2.compareTo(c1));
        //s.sorted(Streaming::miComparador);
        //s.sorted(Streaming::reverseOrder); // DOES NOT COMPILE

        List<String> zero = Arrays.asList();
        List<String> one = Arrays.asList("Bonobo");
        List<String> two = Arrays.asList("Mama Gorilla", "Baby Gorilla");
        Stream<List<String>> animals = Stream.of(zero, one, two);
        animals.flatMap(l -> l.stream()).forEach(System.out::println);

        List<String> listxd = Arrays.asList("Toby", "Anna", "Leroy", "Alex");
        List<String> filtered = new ArrayList<>();
        for (String name: listxd) {
            if (name.length() == 4) filtered.add(name);
        }
        Collections.sort(filtered);
        Iterator<String> iter = filtered.iterator();
        if (iter.hasNext()) System.out.println(iter.next());
        if (iter.hasNext()) System.out.println(iter.next());

        DoubleStream random = DoubleStream. generate (Math::random);
        DoubleStream fractions = DoubleStream. iterate (.5, d -> d / 2);
        random.limit(3).forEach(System.out::println);
        System.out.println();
        fractions.limit(3).forEach(System.out::println);

        Stream<Integer> sx = Stream.of(1);
        IntStream is = sx.mapToInt(x -> x);
        //DoubleStream ds = sx.mapToDouble(x -> x); //throws an exception

        //Stream<Integer> s2 = ds.mapToInt(x -> x); // doesnt compile

        // randoms.forEach(System.out::println); // prints infinite numbers
    }
    //implementa implicitamente la interfaz Comparator, este metodo, NO retorna un comparador
    public static int miComparador(Comparable c1, Comparable c2) {
        return c2.compareTo(c1);
    }

    // NO implementa la interfaz Comparator, sin embargo, RETORNA un comparador
    public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
        return Collections.reverseOrder();
    }


}
