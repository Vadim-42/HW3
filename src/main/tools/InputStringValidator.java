package main.tools;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputStringValidator {

    private Scanner scanner;
    public InputStringValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getValidInput(String hint, String paramMast, int min, int max ) {
        String strVal = "";
        boolean isValid = false;
        do {
            System.out.println(hint + " ( от " + min + " до " + max + " символов)" );
            try {
                strVal = scanner.next();
                isValid = strVal.length() >= min && strVal.length() <= max;
                if (!isValid) {
                    System.out.println(paramMast + " быть не менее " + min + " и не более " + max + " символов" );
                }
            }catch (InputMismatchException e) {
                System.out.println("Количество символов ограничено!");
                scanner.next();
            }
        }while (!isValid);
        scanner.nextLine();
        return strVal;
    }
}
