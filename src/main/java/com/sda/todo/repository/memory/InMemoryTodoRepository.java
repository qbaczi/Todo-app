package com.sda.todo.repository.memory;

import com.sda.todo.model.Todo;
import com.sda.todo.repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryTodoRepository implements TodoRepository {

    private List<Todo> todos;

    public InMemoryTodoRepository() {
        this.todos = new ArrayList<>();
    }

    @Override
    public void save(Todo todo) {
        todos.add(todo);
    }

    @Override
    public Optional<Todo> findById(String id) {
        return todos.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(todos); //zwracac kopie listy
    }
}
