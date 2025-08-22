package book.inmutableObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyOfAnAnimal {
    public static void main(String[] args) {
        //  Create a new Animal instance
        Animal lion = new Animal("lion", 5, Arrays.asList("meat", "moremeat"));

        //  Create a new Animal instance using data from the first instance
        List<String> favoriteFoods = new ArrayList<String>();
        for(int i = 0; i < lion.getFavoriteFoodsCount(); i++)
            favoriteFoods.add(lion.getFavoriteFood(i));
        Animal updatedLion = new Animal(lion.getSpecies(), lion.getAge() + 1, favoriteFoods);
    }
}
