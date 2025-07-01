package animals.pets;

import animals.Animal;
import animals.AnimalType;

public class Cat extends Animal {
    public Cat(String color, String name, int weight, AnimalType type, int age) {
        super(color, name, weight, String.valueOf(type), age);
    }

    @Override
    public void say(){
        System.out.println("Мяу");
    }
}

