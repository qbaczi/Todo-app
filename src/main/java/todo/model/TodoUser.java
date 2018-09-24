package todo.model;

import lombok.Data;

@Data

public class TodoUser {
    private String name;
    private String password;

    public TodoUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public TodoUser() {
    }
}