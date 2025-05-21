package org.example.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.model.Task;
import org.example.tasktracker.security.UserDetailsImpl;
import org.example.tasktracker.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<Task>> getTasksByUserId(@AuthenticationPrincipal UserDetailsImpl user) {

        List<Task> tasksByUserId = taskService.getTasksByUserId(user.getUser().getId());

        return ResponseEntity.ok(tasksByUserId);
    }

    @PostMapping()
    public ResponseEntity<Task> addTask(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody Task task) {
        task.setUserId(user.getUser().getId());
        Task task1 = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {

        task.setId(id);
        Task updatedTask = taskService.updateTask(task);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}