package animals.bird;

import animals.Animal;
import animals.AnimalType;

public class Duck extends Animal {
    public Duck(String color, String name, int weight, AnimalType type, int age) {
        super(color, name, weight, String.valueOf(type), age);
    }

    public void fly() {
        System.out.println("Я лечу");
    }

    @Override
    public void say(){
        System.out.println("Кря");
    }


}
