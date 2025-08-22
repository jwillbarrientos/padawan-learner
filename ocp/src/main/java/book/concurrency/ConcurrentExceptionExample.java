package book.concurrency;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcurrentExceptionExample {
    public static void main(String[] args) {
        Map<String, Object> foodData = new HashMap<>();
        foodData.put("penguin", 1);
        foodData.put("flamingo", 2);
        Iterator<String> iterator = foodData.keySet().iterator();

        //for(String key : foodData.keySet())
        //    foodData.remove(key);

        while(iterator.hasNext()) {
            String key = iterator.next();
//            foodData.remove(key);
//            iterator.remove();
        }

//        while(iterator.hasNext()) {
//            iterator.next();
//            iterator.remove();
//        }

        System.out.println(foodData.size());

//        for(String key : foodData.keySet()) {
//            System.out.println(key);
//        }

//        Iterator<String> it = foodData.keySet().iterator();
//        while(it.hasNext()) {
//            String key = it.next();
//            {
//            System.out.println(key);
//            }
//        }
//
//        System.out.println();

        Arrays.asList("jackal","kangaroo","lemur")
                .parallelStream()
                .map(s -> s.toUpperCase())
                .forEach(System.out::println);

        System.out.println();


//        Arrays.asList("jackal","kangaroo","lemur")
//                .parallelStream()
//                .map(s -> {System.out.println(s); return s.toUpperCase();})
//                .forEach(System.out::println);

        List<String> arrayList1 = new CopyOnWriteArrayList<>();
        List<String> arrayList2 = new CopyOnWriteArrayList<>();

        Arrays.asList("jackal","kangaroo","lemur")
                .parallelStream()
                .map(s -> {arrayList1.add(s); return s.toUpperCase();})
                .forEach(arrayList2::add);

        for(int i = 0; i < arrayList1.size(); i++) {
            System.out.println(arrayList1.get(i));
            System.out.println(arrayList2.get(i));
        }

        System.out.println();

        List<Integer> data = Collections.synchronizedList(new ArrayList<>());
        Arrays.asList(1,2,3,4,5,6).parallelStream()
                .map(i -> {data.add(i); return i;}) // AVOID STATEFUL LAMBDA EXPRESSIONS!
                .forEachOrdered(i -> System.out.print(i+" "));
        System.out.println();
        for(Integer e: data) {
            System.out.print(e+" ");
        }

        System.out.println(Arrays.asList("w","o","l","f")
                .parallelStream()
                .reduce("X",String::concat));

        Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
        Set<String> set = stream.collect(Collectors.toSet());
        System.out.println(set); // [f, w, l, o]
    }
}
