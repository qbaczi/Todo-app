package com.sda.todo.repository;

import com.sda.todo.model.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    void save(Todo todo);

    Optional<Todo> findById(String id);

    List<Todo> findAll();
}
