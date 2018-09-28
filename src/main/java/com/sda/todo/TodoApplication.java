package com.sda.todo;

import com.sda.todo.model.Command;
import com.sda.todo.model.Todo;
import com.sda.todo.model.TodoStatus;
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

import java.util.Optional;
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
                    listTodoList();
                    break;
                default:
                    todoConsoleView.exit();
                    flagExit = false;
                    break;
            }
        } while (flagExit);
    }

    private void listTodoList() {
        Integer option = todoConsoleView.showTodoListWithOption(todoService.findAllTodo());
        Command command = new Command(option);

        switch (option) {
            case 1:
                String possibleId = todoConsoleView.getPossibleId();
                Integer todoId = extractTodoId(possibleId);
                command.addArgument("todoId", todoId);
                showTodo(command);
                break;
            case 2:
                String possibleIdToRemove = todoConsoleView.getPossibleId();
                Integer todoIdToRemove = extractTodoId(possibleIdToRemove);
                command.addArgument("todoId", todoIdToRemove);
                removeTodo(command);
                break;
            case 3:
                String possibleIdToAssign = todoConsoleView.getPossibleId();
                Integer todoIdToAssign = extractTodoId(possibleIdToAssign);
                command.addArgument("todoId", todoIdToAssign);
                command.addArgument("user", currentUser);
                assign(command);
                break;
            case 4:
                addChangeStatusArguments(command);
                changeStatus(command);
                break;
        }
    }

    private void addChangeStatusArguments(Command command) {
        String restOfCommond = todoConsoleView.getPossibleId();
        Scanner scanner = new Scanner(restOfCommond);
        if (scanner.hasNextInt()) {
            command.addArgument("todoId", scanner.nextInt());
        } else {
            command.addArgument("todoId", todoConsoleView.getTodoId());
        }
        if (scanner.hasNext()) {
            String status = scanner.next();
            command.addArgument("status", TodoStatus.valueOf(status));
        } else {
            command.addArgument("status", todoConsoleView.getStatus());
        }
    }

    private void changeStatus(Command command) {
        Integer todoId = (Integer) command.getArgument("todoId")-1;
        TodoStatus status = (TodoStatus) command.getArgument("status");
        Optional<Todo> todo = todoService.findTodoById(todoId);
        if (todo.isPresent()) {
            Todo todoToChangeStatus = todo.get();
            todoToChangeStatus.setTodoStatus(status);
        }
        todoConsoleView.displayChangeStatus(todo);
    }

    private void assign(Command command) {

        Integer todoId = (Integer) command.getArgument("todoId");
        TodoUser user = (TodoUser) command.getArgument("user");
        Optional<Todo> todo = todoService.findTodoById(todoId);
        if (todo.isPresent()) {
            Todo todoToChangeAssignment = todo.get();
            todoToChangeAssignment.setOwner(user);
        }
        todoConsoleView.displayAssignment(todo, user);
    }

    private void removeTodo(Command command) {

        Integer todoId = (Integer) command.getArgument("todoId");
        Optional<Todo> removedTodo = todoService.removeTodo(todoId);
        todoConsoleView.displayTodoRemove(removedTodo);

    }

    private Integer extractTodoId(String possibleId) {

        Integer todoId;
        if (possibleId.length() == 0) {
            todoId = todoConsoleView.getTodoId()-1;
        } else {
            todoId = Integer.valueOf(possibleId)-1;
        }
        return todoId;
    }

    private void showTodo(Command command) {

        Integer todoId = (Integer) command.getArgument("todoId");
        Optional<Todo> todo = todoService.findTodoById(todoId);
        todoConsoleView.showTodoWithDetails(todo);
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
        if (currentUser != null) {

            String todoName = todoConsoleView.createNewTodoName();
            String todoDescription = todoConsoleView.createNewTodoDescription();
            Todo todo = new Todo(todoName, this.currentUser);
            todo.setDescription(todoDescription);

            todoService.save(todo);
        }
    }
}
