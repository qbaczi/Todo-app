package com.sda.todo;

import com.sda.todo.model.Todo;
import com.sda.todo.model.TodoUser;
import com.sda.todo.model.exception.InvalidPasswordException;
import com.sda.todo.model.exception.TodoUserDoesNotExistsException;
import com.sda.todo.repository.TodoRepository;
import com.sda.todo.repository.TodoUserRepository;
import com.sda.todo.repository.memory.InMemoryTodoRepository;
import com.sda.todo.repository.memory.InMemoryTodoUserRepository;
import com.sda.todo.service.TodoService;
import com.sda.todo.views.TodoConsoleView;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Scanner;

@AllArgsConstructor
public class TodoApplication {

    private TodoService todoService;
    private TodoConsoleView todoConsoleView;
    private TodoUser currentUser;

    public static void main(String[] args) {
        TodoRepository todoRepository = new InMemoryTodoRepository();
        TodoUserRepository todoUserRepository = new InMemoryTodoUserRepository();
        TodoService todoService = new TodoService(todoRepository, todoUserRepository);

        Scanner scanner = new Scanner(System.in);
        TodoConsoleView todoConsoleView = new TodoConsoleView(scanner);

        TodoApplication todoApplication = new TodoApplication(todoService, todoConsoleView, null);
        todoApplication.start();
    }

    public void start() {
        boolean flagExit = true;
        do {
            Integer menuOption = todoConsoleView.menu();
            switch (menuOption) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    addNewTodo();
                    break;
                case 4:

                    break;
                default:
                    todoConsoleView.exit();
                    flagExit = false;
                    break;
            }
        } while (flagExit);
    }


    private void register() {
        String name = todoConsoleView.registerName();
        String password = todoConsoleView.registerPassword();
        TodoUser user = todoService.register(name, password);

        if (user == null) {
            todoConsoleView.displayError("User already exist");
        } else {
            todoConsoleView.displaySuccess("User registered");
        }

    }

    private void login() {
        this.currentUser = null;
        String name = todoConsoleView.logInName();
        String password = todoConsoleView.logInPassword();
        try {
            this.currentUser = todoService.login(name, password);
        } catch (TodoUserDoesNotExistsException | InvalidPasswordException e) {
            todoConsoleView.displayError(e.getMessage());
        }

        if (this.currentUser != null) {
            todoConsoleView.displaySuccess("User log in");
        }
    }

    private void addNewTodo() {
        if (currentUser == null) {
            login();
        }

        String todoName = todoConsoleView.createNewTodoName();
        String todoDescription = todoConsoleView.createNewTodoDescription();
        Todo todo = new Todo(todoName, this.currentUser);
        todo.setDescription(todoDescription);

        todoService.save(todo);
    }
}
