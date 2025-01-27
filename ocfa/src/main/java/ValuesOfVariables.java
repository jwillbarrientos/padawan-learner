public class ValuesOfVariables {
    public static void main(String[] args) {
        int m = 50; // m is 50
        System.out.println("m: " + m);
        int n = ++m; // n and m are now 51
        System.out.println("n: " + n + ", m: " + m);
        int o = m--; // o is 51, m becomes 50
        System.out.println("o: " + o + ", n: " + n + ", m: " + m);
        int p = --o + m--; // p is 49 + 50, m becomes 49
        System.out.println("p: " + p + ", o: " + o + ", n: " + n + ", m: " + m);
        int x = m < n ? n < o ? o < p ? p : o : n : m; // Confusing
        System.out.println("x: " + x + ", p: " + p + ", o: " + o + ", n: " + n + ", m: " + m);
    }
}
