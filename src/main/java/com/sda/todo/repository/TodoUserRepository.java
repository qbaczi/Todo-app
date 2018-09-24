package com.sda.todo.repository;

import com.sda.todo.model.TodoUser;

public interface TodoUserRepository {
    boolean save(TodoUser user);

    TodoUser findByName(String name);

    boolean exists(String name);
}
