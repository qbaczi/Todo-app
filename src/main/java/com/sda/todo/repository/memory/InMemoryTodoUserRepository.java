package com.sda.todo.repository.memory;

import com.sda.todo.model.TodoUser;
import com.sda.todo.repository.TodoUserRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class InMemoryTodoUserRepository implements TodoUserRepository {

    private List<TodoUser> users;

    public InMemoryTodoUserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public boolean save(TodoUser user) {
        if (exists(user.getName())) {
            return false;
        }
        return users.add(user);
    }

    @Override
    public TodoUser findByName(String name) {
        return users.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
//                .orElseGet(() -> null);
                .orElse(null);
    }

    @Override
    public boolean exists(String name) {
        return users.stream()
                .anyMatch(e -> e.getName().equals(name));
    }
}
