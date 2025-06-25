package main;

import animals.Animal;
import data.AnimalFactory;
import db.MySQLConnector;
import tables.AnimalTable;
import main.tools.AnimalCreator;
import main.tools.InputIntValidator;
import main.tools.InputStringValidator;
import menu.Command;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Объявляем сканер для чтения ввода пользователя
    private static Scanner scanner = new Scanner(System.in);
    // Валидаторы для проверки корректности ввода чисел и строк
    private static InputIntValidator intValidator = new InputIntValidator(scanner);
    private static InputStringValidator strValidator = new InputStringValidator(scanner);
    // Класс для создания объектов Animal на основе пользовательского ввода
    private static AnimalCreator animalCreator = new AnimalCreator(scanner, intValidator, strValidator);

    public static void main(String[] args) throws SQLException {
        // Создаем объект для работы с таблицей животных в базе данных
        AnimalTable animalTable = new AnimalTable();

        // Инициализируем таблицу: удаляем старую и создаем новую с нужными колонками
        initializeAnimalTable(animalTable);

        // Главный цикл программы, который работает постоянно
        while (true) {
            // Запрашиваем у пользователя команду
            Command command = promptUserForCommand();
            // Выполняем выбранную команду
            executeCommand(command, animalTable);
        }
    }

    /**
     * Метод инициализирует таблицу животных:
     * удаляет существующую и создает новую с заданными колонками.
     */
    private static void initializeAnimalTable(AnimalTable animalTable) throws SQLException {
        animalTable.delete(); // Удаляем таблицу, если она существует

        // Определяем список колонок для новой таблицы
        List<String> columnsAnimalTable = new ArrayList<>();
        columnsAnimalTable.add("id INT AUTO_INCREMENT PRIMARY KEY"); // Уникальный идентификатор
        columnsAnimalTable.add("type VARCHAR(20)");   // Тип животного
        columnsAnimalTable.add("name VARCHAR(20)");   // Имя животного
        columnsAnimalTable.add("color VARCHAR(20)");  // Цвет животного
        columnsAnimalTable.add("age INT");             // Возраст животного
        columnsAnimalTable.add("weight INT");          // Вес животного

        animalTable.create(columnsAnimalTable); // Создаем таблицу с указанными колонками
    }

    /**
     * Метод запрашивает у пользователя команду из перечисления Command.
     * Если введена некорректная команда, просит ввести снова.
     * @return выбранная команда типа Command
     */
    private static Command promptUserForCommand() {
        // Получаем список всех возможных названий команд для подсказки пользователю
        List<String> commandNames = new ArrayList<>();
        for (Command commandData : Command.values()) {
            commandNames.add(commandData.name());
        }

        System.out.printf("Введите команду (%s): ", String.join("/", commandNames));

        Command command = null;

        do {
            String input = scanner.nextLine().toUpperCase().trim(); // Считываем и нормализуем ввод
            try {
                command = Command.valueOf(input); // Пробуем преобразовать строку в enum Command
            } catch (IllegalArgumentException e) {
                System.out.print("Некорректная команда. Пожалуйста, попробуйте снова: ");
            }
        } while (command == null); // Повторяем, пока не будет введена корректная команда

        return command;
    }

    /**
     * Метод выполняет действие в зависимости от выбранной команды.
     * @param command команда для выполнения
     * @param animalTable объект для работы с таблицей животных
     */
    private static void executeCommand(Command command, AnimalTable animalTable) throws SQLException {
        switch (command) {
            case ADD:
                addNewAnimal(animalTable);
                break;

            case LIST:
                listAnimals(animalTable);
                break;

            // Здесь можно добавить обработку других команд

            default:
                System.out.println("Неизвестная команда.");
                break;
        }
    }

    /**
     * Метод добавляет нового животного в базу данных.
     * Запрашивает у пользователя данные животного и сохраняет его.
     */
    private static void addNewAnimal(AnimalTable animalTable) {
        try {
            // Создаем новое животное, запрашивая у пользователя данные
            Animal newAnimal = animalCreator.createAnimalWithData("Введите тип животного");
            // Записываем животное в базу данных
            animalTable.write(newAnimal);
        } catch (IllegalArgumentException e) {
            // Если введены некорректные данные — выводим сообщение об ошибке
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод выводит список животных из базы данных.
     * Позволяет вывести весь список или отфильтровать по типу животного.
     */
    private static void listAnimals(AnimalTable animalTable) throws SQLException {
        if (animalTable.isTableEmpty()) {
            System.out.println("Список пуст. Добавьте животное.");
            return; // Если таблица пустая — выходим из метода
        }

        System.out.println("Выберите вариант вывода списка:\n1 - Весь список\n2 - Список по типам животных");
        String filter = scanner.nextLine();

        switch (filter) {
            case "1":
                // Получаем полный список животных из базы и выводим их на экран
                List<Animal> animals = animalTable.read();
                for (Animal animal : animals) {
                    System.out.println(animal);
                }
                break;
            case "2":
                // Запрашиваем тип животного для фильтрации списка
                System.out.printf("Введите тип животного, который хотите вывести (%s): ", String.join(" / ", AnimalFactory.ANIMAL_TYPES));
                String animalType = scanner.nextLine();
                break;
            default:
                System.out.println("Некорректный выбор.");
                break;
        }
    }
}
