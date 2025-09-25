package enthuwareOcp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Student {
    private String name;
    private int marks;

    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public void addMarks(int m) {
        this.marks += m;
    }

    public void debug() {
        System.out.println(name + ":" + marks);
    }

    public static void main(String[] args) {
        List<Student> sList = Arrays.asList(new Student("S1", 40), new Student("S2", 35), new Student("S3", 30));
        Consumer<Student> increaseMarks = s -> s.addMarks(10);
        sList.forEach(increaseMarks);
        sList.stream().forEach(Student::debug);
    }
}
