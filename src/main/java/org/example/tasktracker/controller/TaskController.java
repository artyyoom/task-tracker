package org.example.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.model.Task;
import org.example.tasktracker.model.User;
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
    public ResponseEntity<List<Task>> getTasksByUserId(@AuthenticationPrincipal User user) {

        List<Task> tasksByUserId = taskService.getTasksByUserId(user);

        return ResponseEntity.ok(tasksByUserId);
    }

    @PostMapping()
    public ResponseEntity<Task> addTask(@AuthenticationPrincipal User user, @RequestBody Task task) {
        Task task1 = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task1);
    }
}