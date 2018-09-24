package todo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Getter
@Setter
public class Todo extends TodoUser {

    private String id;
    private String name;
    private String descripton;
    private Instant creationDate;
    private TodoUser owner;
    private TodoStatus todoStatus;

    public Todo(String name, Instant creationDate) {

        this.name = name;
        this.creationDate = creationDate;
        this.descripton = "";
        this.creationDate = Instant.now();
        this.owner = null;
        this.todoStatus = TodoStatus.New;
        this.id = UUID.randomUUID().toString();
    }
}
