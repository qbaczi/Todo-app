package com.sda.todo.service;

import com.sda.todo.model.Todo;
import com.sda.todo.model.TodoUser;
import com.sda.todo.model.exception.InvalidPasswordException;
import com.sda.todo.model.exception.TodoUserDoesNotExistsException;
import com.sda.todo.repository.TodoRepository;
import com.sda.todo.repository.TodoUserRepository;
import lombok.AllArgsConstructor;

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

    public TodoUser login(String name, String password) {
        if (!todoUserRepository.exists(name)) {
            throw new TodoUserDoesNotExistsException("User with name \"" + name + "\" does not exists");
        }
        TodoUser user = todoUserRepository.findByName(name);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password");
        }
        return user;
    }
}
