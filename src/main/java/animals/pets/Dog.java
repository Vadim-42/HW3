package animals.pets;

import animals.Animal;
import animals.AnimalType;

public class Dog extends Animal {
    public Dog(String color, String name, int weight, AnimalType type, int age) {
        super(color, name, weight, String.valueOf(type), age);
    }

    @Override
    public void say(){
        System.out.println("Гав");
    }
}
