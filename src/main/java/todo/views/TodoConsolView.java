package todo.views;

import java.util.Scanner;

public class TodoConsolView {
    private Scanner scanner;

    public TodoConsolView(Scanner scanner) {
        this.scanner = scanner;
    }

    public Integer menu() {
        System.out.println("Todo application");
        System.out.println("1. Zaloguj");
        System.out.println("2. Zarejestruj");
        System.out.println("3. Dodaj zadanie");
        System.out.println("4. Wyświetl zadania");
        System.out.println("0. Koniec");

        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public String loginName() {
        System.out.println("Podaj nick");
        return scanner.nextLine();
    }

    public String loginPassword() {
        System.out.println("Podaj hasło");
        return scanner.nextLine();
    }

    public String registerName() {
        return loginName();
    }

    public String registerPassword() {
        return loginPassword();
    }

    public String createNewTodoName() {
        System.out.println("Podaj nazwę zadania");
        return scanner.nextLine();
    }

    public String createNewTodoDescription() {
        System.out.println("Podaj opis zadania");
        return scanner.nextLine();
    }
}
