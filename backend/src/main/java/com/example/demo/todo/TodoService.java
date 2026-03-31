package com.example.demo.todo;

import org.springframework.stereotype.Service;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryRepository;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    public TodoService(TodoRepository todoRepository, CategoryRepository categoryRepository) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found: " + id));
    }

    public Todo createTodo(TodoCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setCompleted(request.isCompleted());
        todo.setDescription(request.getDescription());
        todo.setCategory(category);

        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo existing = getTodoById(id);
        existing.setTitle(updatedTodo.getTitle());
        existing.setCompleted(updatedTodo.isCompleted());
        return todoRepository.save(existing);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}