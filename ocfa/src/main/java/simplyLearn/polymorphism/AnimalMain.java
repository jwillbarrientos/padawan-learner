package simplyLearn.polymorphism;

public class AnimalMain {
    public static void main(String[] args) {
        AnimalSounds animal = new AnimalSounds();
        animal.Sound();
        Cow cow = new Cow();
        cow.Sound();
        Dog dog = new Dog();
        dog.Sound();
    }
}
