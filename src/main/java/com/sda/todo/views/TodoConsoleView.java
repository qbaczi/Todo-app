package com.sda.todo.views;

import com.sda.todo.model.Todo;
import com.sda.todo.model.TodoStatus;
import com.sda.todo.model.TodoUser;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class TodoConsoleView {
    private Scanner scanner;

    public TodoConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public Integer menu() {
        System.out.println("************************");
        System.out.println("Todo application");
        System.out.println("1. Zaloguj");
        System.out.println("2. Zarejestruj");
        System.out.println("3. Dodaj zadanie");
        System.out.println("4. Wyswietl zadania");
        System.out.println("0. Koniec");
        System.out.println("*************************");

        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public String logInName() {
        System.out.println("Podaj nick");
        return scanner.nextLine();
    }

    public String logInPassword() {
        System.out.println("Podaj haslo");
        return scanner.nextLine();
    }

    public String registerName() {
        return logInName();
    }

    public String registerPassword() {
        return logInPassword();
    }

    public String createNewTodoName() {
        System.out.println("Podaj nazwe zadania");
        return scanner.nextLine();
    }

    public String createNewTodoDescription() {
        System.out.println("Podaj opis zadania");
        return scanner.nextLine();
    }

    public Integer getTodoId() {
        System.out.println("Podaj numer zadania");
        int todoId = scanner.nextInt();
        scanner.nextLine();
        return todoId;
    }

    public void displayError(String message){

        System.out.println("Error");
        System.out.println(message);
        System.out.println("Error");

    }

    public void displaySuccess(String message) {

        System.out.println("Success");
        System.out.println(message);
        System.out.println("Success");

    }

    public void exit() {

        System.out.println("Zapraszamy ponownie");
    }

    public Integer showTodoListWithOption(List<Todo> allTodo) {
        System.out.println("***Lista Zadań***");
        System.out.println("******************");
        for (int i = 0; i < allTodo.size(); i++) {
            Todo todo = allTodo.get(i);
            Optional<TodoUser> owner = Optional.ofNullable(todo.getOwner());
            TodoUser creator = todo.getCreator();
            TodoStatus todoStatus = todo.getTodoStatus();
            System.out.println((i + 1) +
                    ". | \"" + todo.getName() +
                    "\" |\"" + creator.getName() +
                    "\" | \"" + owner.orElse(TodoUser.unasigned()).getName() +
                    "\" |" + todoStatus.toString().toUpperCase());

        }
        System.out.println("**************************");
        System.out.println("1. Wyświetl");
        System.out.println("2. Usuń");
        System.out.println("3. Przypisz");
        System.out.println("4. Zmień status");
        System.out.println("0. Wyjdz");

        Integer option = scanner.nextInt();

        return option;
    }

    public void showTodoWithDetails(Optional<Todo> todo) {
        String message = todo.map(e -> {
            TodoUser creator = e.getCreator();
            Optional<TodoUser> owner = Optional.ofNullable(e.getOwner());
            return e.getName() + " (" + e.getTodoStatus().toString() + ") (" + e.getCreationDate().toString() + ")\n" +
                    e.getDescription() + "\n" +
                    "Tworca: " + creator.getName() + "\n" +
                    "Przypisane: " + owner.orElse(TodoUser.unasigned()).getName();
        }).orElse("Zadanie nie istnieje");

        System.out.println(message);
    }

    private Integer askForIdToRemove() {
        System.out.println("Podaj ID zadania");
        int toDoId = scanner.nextInt();
        scanner.nextLine();
        return toDoId;
    }

    public String getPossibleId() {
        String possibleId = scanner.nextLine();
        if (possibleId.length() > 0) {
            return possibleId.substring(1);
        } else {
            return possibleId;
        }
    }

    public void displayTodoRemove(Optional<Todo> removedTodo) {
        System.out.println(removedTodo.map(e -> "Usunieto zadania " + e.getName()).orElse("Zadanie nie istnieje"));
    }

    public void displayAssignment(Optional<Todo> todo, TodoUser currentUser) {
        todo.map(e -> "Przypisano " + currentUser.getName() + " do zadania \"" + e.getName() + "\"").orElse("Zadanie nie istnieje!");

    }

    public void displayChangeStatus(Optional<Todo> todo) {
        System.out.println(todo.map(e -> "Zmieniono status zadania " + e.getName() + " na " + e.getTodoStatus()).orElse("Zadanie nie istnieje"));

    }

    public TodoStatus getStatus() {
        System.out.println("Podaj status (New, Active, Closed)");
        String status = scanner.nextLine();
        return TodoStatus.valueOf(status);

    }
}
