package book.InmutableObjects;

import java.util.ArrayList;
import java.util.List;

/*
    Immutable Object: read-only objects that can be shared and used by multiple classes

    Rules to an Object can be Immutable
    1. Use a constructor to set all properties of the object.
    2. Mark all of the instance variables private and final .
    3. Don’t define any setter methods.
    4. Don’t allow referenced mutable objects to be modified or accessed directly.
    5. Prevent methods from being overridden.
 */
public final class Animal {
    private final String species;
    private final int age;
    private final List<String> favoriteFoods;

    public Animal(String species, int age, List<String> favoriteFoods) {
        this.species = species;
        this.age = age;
        if(favoriteFoods == null)
            throw new RuntimeException("favoriteFoods is required");
        this.favoriteFoods = new ArrayList<String>(favoriteFoods);
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public int getFavoriteFoodsCount() {
        return favoriteFoods.size();
    }

    public String getFavoriteFood(int index) {
        return favoriteFoods.get(index);
    }

    // you should never share references to a mutable object contained within an immutable object
    //public List<String> getFavoriteFoods() {    //  MAKES CLASS MUTABLE, users have direct access to the list and can change the objects in the list
    //    return favoriteFoods;
    //}
}
