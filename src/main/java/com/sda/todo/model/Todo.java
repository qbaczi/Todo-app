package com.sda.todo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Todo {
    private String id;
    private String name;
    private TodoUser creator;
    private String description;
    private Instant creationDate;
    private TodoUser owner;
    private TodoStatus todoStatus;

    public Todo(String name, TodoUser creator) {
        this.name = name;
        this.creator = creator;
        this.description = "";
        this.creationDate = Instant.now();
        this.owner = null;
        this.todoStatus = TodoStatus.New;
        this.id = UUID.randomUUID().toString();
    }
}
