package animals.bird;

import animals.Animal;

public class Duck extends Animal {
    public Duck(String color, String name, int weight, String type, int age) {
        super(color, name, weight, type, age);
    }

    public void fly() {
        System.out.println("Я лечу");
    }

    @Override
    public void say(){
        System.out.println("Кря");
    }


}
