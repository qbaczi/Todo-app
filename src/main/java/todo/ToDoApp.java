package todo;

import lombok.AllArgsConstructor;
import todo.model.Todo;
import todo.repository.TodoRepository;
import todo.repository.TodoUserRepository;
import todo.repository.memory.InMemoryTodoRepository;
import todo.repository.memory.InMemoryTodoUserRepository;
import todo.service.TodoService;
import todo.views.TodoConsolView;

import java.time.Instant;
import java.util.Scanner;

@AllArgsConstructor
public class ToDoApp {

    private TodoService todoService;
    private TodoConsolView todoConsolView;
    private Instant correntUser;

    public static void main(String[] args) {
        TodoRepository todoRepository = new InMemoryTodoRepository();
        TodoUserRepository todoUserRepository = new InMemoryTodoUserRepository();
        TodoService todoService = new TodoService(todoRepository, todoUserRepository);

        Scanner scanner = new Scanner(System.in);
        TodoConsolView todoConsolView = new TodoConsolView(scanner);

        ToDoApp toDoApp = new ToDoApp(todoService, todoConsolView, null);

        toDoApp.start();

    }

    public void start() {

        do {
            Integer menuOption = todoConsolView.menu();
            switch (menuOption) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    addNewTodo();
                    break;
                case 4:
                    break;
                default:
                    break;

            }
        } while (true);

    }

    private void addNewTodo() {
        if (correntUser == null) {
            String name = todoConsolView.loginName();
            String password = todoConsolView.loginPassword();
            this.correntUser = todoService.login(name, password);
        }
        String todoName = todoConsolView.createNewTodoName();
        String todoNameDescription = todoConsolView.createNewTodoDescription();
        Todo todo= new Todo(todoName, this.correntUser);
        todo.setDescripton(todoNameDescription);


//        todoService.save();
    }
}
