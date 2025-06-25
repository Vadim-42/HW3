package main.tools;

import java.util.Scanner;


public class AnimalCreator {
    private Scanner scanner;
    private  main.tools.InputIntValidator intValidator;
    private  main.tools.InputStringValidator strValidator;

    public AnimalCreator(Scanner scanner,  main.tools.InputIntValidator intValidator,  main.tools.InputStringValidator strValidator) {
        this.scanner = scanner;
        this.intValidator = intValidator;
        this.strValidator = strValidator;
    }

    public boolean inputStringValidator(String str) {
        return str.matches("^[a-zA-Z0-9]+$");
    }

    public animals.Animal createAnimalWithData (String hintText){
        String type;
        boolean rightType = false;
        do {
            System.out.printf("%s %s: ", hintText, String.join("/", data.AnimalFactory.ANIMAL_TYPES));
            type = scanner.next().trim().toUpperCase();
            if (data.AnimalFactory.ANIMAL_TYPES.contains(type)) {
                rightType = true;
            } else {
                System.out.println("Неизвестное животное, попробуйте еще раз");
            }
        }while (!rightType);

        System.out.println("Как зовут животное?");
        String name;
        do {
            name = scanner.next().trim();
            if (!inputStringValidator(name)) {
                System.out.println("Некорректное имя, попробуйте еще раз");
            }
        } while (!inputStringValidator(name));

        int age = intValidator.getValidInput("Сколько ему лет?", "Возраст должен", 1, 20);

        int weight = intValidator.getValidInput("Сколько оно весит?", "Вес должен", 1, 100);

        System.out.println("Какого цвета животное?");
        String color;
        do {
            color = scanner.nextLine().trim();
            if (!inputStringValidator(color)) {
                System.out.println("Некорректный цвет, попробуйте еще раз");
            }
        } while (!inputStringValidator(color));

        return data.AnimalFactory.createAnimal(type, name, age, weight, color);
    }
}
