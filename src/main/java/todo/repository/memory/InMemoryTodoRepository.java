package todo.repository.memory;

import todo.model.Todo;
import todo.model.TodoUser;
import todo.repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryTodoRepository implements TodoRepository {

    private List<TodoUser> todos;

    public InMemoryTodoRepository() {
        this.todos = new ArrayList();

    }

    @Override
    public void save(Todo todo) {
       todos.add(todo);
    }

    @Override
    public Optional<Todo> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Todo> findAll() {

        return new ArrayList(todos);
    }
}
