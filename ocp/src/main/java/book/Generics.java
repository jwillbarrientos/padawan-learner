package book;

public class Generics {
    public static class Crate<T> {
        private T contents;
        T[] array;
        public T emptyCrate() {
            return contents;
        }
        public void packCrate(T contents) {
            this.contents = contents;
        }

        public static <T> Crate<T> ship(T t) {
            System.out.println("Preparing " + t);
            return new Crate<T>();
        }
    }

    public static void main(String[] args) {
        Integer[] array2 = {1, 2, 3};

        Crate<Integer> xd = new Crate<Integer>();
        xd.array = array2;

    }
}
