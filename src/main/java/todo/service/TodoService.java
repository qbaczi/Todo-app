package todo.service;

import lombok.AllArgsConstructor;
import todo.model.Todo;
import todo.model.TodoUser;
import todo.model.exception.InvalidPasswordException;
import todo.model.exception.TodoUserAlreadyExistsException;
import todo.repository.TodoRepository;
import todo.repository.TodoUserRepository;

import java.time.Instant;

@AllArgsConstructor
public class TodoService {

    private TodoRepository todoRepository;
    private TodoUserRepository todoUserRepository;


    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public TodoUser register(String name, String password) {
        if (todoUserRepository.exists(name)) {
            return null;
        }
        TodoUser user = new TodoUser(name, password);
        todoUserRepository.save(user);
        return user;

    }

    public Instant login(String name, String password) {
        if (!todoUserRepository.exists(name)) {
            throw new TodoUserAlreadyExistsException("User wit name" + name + " already exists");
        }

        TodoUser user = todoUserRepository.findByName(name);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password");
        }
        return user;
    }
}